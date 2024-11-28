package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Course;
import io.github.wkktoria.edux.model.EduxClass;
import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.service.CoursesService;
import io.github.wkktoria.edux.service.EduxClassService;
import io.github.wkktoria.edux.service.PersonService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
class AdminController {
    private final EduxClassService eduxClassService;
    private final PersonService personService;
    private final CoursesService coursesService;

    @Autowired
    AdminController(final EduxClassService eduxClassService,
                    final PersonService personService,
                    final CoursesService coursesService) {
        this.eduxClassService = eduxClassService;
        this.personService = personService;
        this.coursesService = coursesService;
    }

    @RequestMapping("/displayClasses")
    ModelAndView displayClasses(Model model) {
        List<EduxClass> eduxClasses = eduxClassService.findAll();

        ModelAndView modelAndView = new ModelAndView("admin/classes");
        modelAndView.addObject("eduxClass", new EduxClass());
        modelAndView.addObject("eduxClasses", eduxClasses);
        return modelAndView;
    }

    @RequestMapping("/addNewClass")
    ModelAndView addNewClass(Model model, @ModelAttribute("eduxClass") EduxClass eduxClass) {
        eduxClassService.saveClass(eduxClass);

        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @RequestMapping("/deleteClass")
    ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<EduxClass> eduxClass = eduxClassService.findClassWithId(id);
        for (Person person : eduxClass.get().getPersons()) {
            person.setEduxClass(null);
            personService.updatePerson(person);
        }
        eduxClassService.deleteClassWithId(id);

        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @RequestMapping("/displayStudents")
    ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                 @RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("admin/students");
        Optional<EduxClass> eduxClass = eduxClassService.findClassWithId(classId);
        modelAndView.addObject("eduxClass", eduxClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("eduxClass", eduxClass.get());

        String message;
        if (error != null) {
            message = "Invalid email address entered.";
            modelAndView.addObject("errorMessage", message);
        }

        return modelAndView;
    }

    @PostMapping("/addStudent")
    ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        EduxClass eduxClass = (EduxClass) session.getAttribute("eduxClass");
        Person personEntity = personService.findPersonWithEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eduxClass.getClassId() + "&error=true");
            return modelAndView;
        }

        personEntity.setEduxClass(eduxClass);
        personService.updatePerson(personEntity);
        eduxClass.getPersons().add(personEntity);
        eduxClassService.updateClass(eduxClass);

        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eduxClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        EduxClass eduxClass = (EduxClass) session.getAttribute("eduxClass");
        Optional<Person> person = personService.findPersonWithId(personId);
        person.get().setEduxClass(null);
        eduxClass.getPersons().remove(person.get());
        eduxClassService.updateClass(eduxClass);
        session.setAttribute("eduxClass", eduxClass);

        return new ModelAndView("redirect:/admin/displayStudents?classId=" + eduxClass.getClassId());
    }

    @RequestMapping("/displayCourses/page/{pageNum}")
    ModelAndView displayCourses(Model model,
                                @PathVariable(name = "pageNum") final int pageNum,
                                @RequestParam("sortField") final String sortField,
                                @RequestParam("sortDir") final String sortDir) {
        Page<Course> coursesPage = coursesService.findAll(pageNum, sortField, sortDir);
        List<Course> courses = coursesPage.getContent();

        ModelAndView modelAndView = new ModelAndView("admin/courses");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Course());

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", coursesPage.getTotalPages());
        model.addAttribute("totalCourses", coursesPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    ModelAndView addNewStudent(Model model, @ModelAttribute("course") Course course) {
        ModelAndView modelAndView = new ModelAndView();
        coursesService.saveCourse(course);
        modelAndView.setViewName("redirect:/admin/displayCourses/page/1?sortField=name&sortDir=asc");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    ModelAndView viewStudents(Model model, @RequestParam final int id,
                              HttpSession session,
                              @RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("admin/course_students");
        Optional<Course> course = coursesService.findCourseWithId(id);
        modelAndView.addObject("course", course.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("course", course.get());

        String message;
        if (error != null) {
            message = "Invalid email address entered.";
            modelAndView.addObject("errorMessage", message);
        }

        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person,
                                    HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Course course = (Course) session.getAttribute("course");
        Person personEntity = personService.findPersonWithEmail(person.getEmail());

        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getCourseId()
                    + "&error=true");
            return modelAndView;
        }

        try {
            personEntity.getCourses().add(course);
            course.getPersons().add(personEntity);
            personService.updatePerson(personEntity);
            session.setAttribute("course", course);
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getCourseId());
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getCourseId()
                    + "&error=true");
        }

        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    ModelAndView deleteStudentFromCourse(Model model, @RequestParam final int personId,
                                         HttpSession session) {
        Course course = (Course) session.getAttribute("course");
        Optional<Person> person = personService.findPersonWithId(personId);
        if (person.isPresent()) {
            person.get().getCourses().remove(course);
            course.getPersons().remove(person.get());
            personService.updatePerson(person.get());
        }
        session.setAttribute("course", course);
        return new ModelAndView("redirect:/admin/viewStudents?id=" + course.getCourseId());
    }

    @GetMapping("/deleteCourse")
    ModelAndView deleteCourse(Model model, @RequestParam final int courseId) {
        Optional<Course> course = coursesService.findCourseWithId(courseId);
        if (course.isPresent()) {
            if (!course.get().getPersons().isEmpty()) {
                for (Person person : course.get().getPersons()) {
                    person.getCourses().remove(course.get());
                }
            }
            coursesService.deleteCourse(course.get());
        }
        return new ModelAndView("redirect:/admin/displayCourses/page/1?sortField=name&sortDir=asc");
    }
}
