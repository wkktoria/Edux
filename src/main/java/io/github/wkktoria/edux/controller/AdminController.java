package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Course;
import io.github.wkktoria.edux.model.EduxClass;
import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.repository.CoursesRepository;
import io.github.wkktoria.edux.repository.EduxClassRepository;
import io.github.wkktoria.edux.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private final EduxClassRepository eduxClassRepository;
    private final PersonRepository personRepository;
    private final CoursesRepository coursesRepository;

    @Autowired
    AdminController(final EduxClassRepository eduxClassRepository,
                    final PersonRepository personRepository,
                    final CoursesRepository coursesRepository) {
        this.eduxClassRepository = eduxClassRepository;
        this.personRepository = personRepository;
        this.coursesRepository = coursesRepository;
    }

    @RequestMapping("/displayClasses")
    ModelAndView displayClasses(Model model) {
        List<EduxClass> eduxClasses = eduxClassRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("admin/classes");
        modelAndView.addObject("eduxClass", new EduxClass());
        modelAndView.addObject("eduxClasses", eduxClasses);
        return modelAndView;
    }

    @RequestMapping("/addNewClass")
    ModelAndView addNewClass(Model model, @ModelAttribute("eduxClass") EduxClass eduxClass) {
        eduxClassRepository.save(eduxClass);

        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @RequestMapping("/deleteClass")
    ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<EduxClass> eduxClass = eduxClassRepository.findById(id);
        for (Person person : eduxClass.get().getPersons()) {
            person.setEduxClass(null);
            personRepository.save(person);
        }
        eduxClassRepository.deleteById(id);

        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @RequestMapping("/displayStudents")
    ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                 @RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("admin/students");
        Optional<EduxClass> eduxClass = eduxClassRepository.findById(classId);
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
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eduxClass.getClassId() + "&error=true");
            return modelAndView;
        }

        personEntity.setEduxClass(eduxClass);
        personRepository.save(personEntity);
        eduxClass.getPersons().add(personEntity);
        eduxClassRepository.save(eduxClass);

        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eduxClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        EduxClass eduxClass = (EduxClass) session.getAttribute("eduxClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setEduxClass(null);
        eduxClass.getPersons().remove(person.get());
        eduxClassRepository.save(eduxClass);
        session.setAttribute("eduxClass", eduxClass);

        return new ModelAndView("redirect:/admin/displayStudents?classId=" + eduxClass.getClassId());
    }

    @GetMapping("/displayCourses")
    ModelAndView displayCourses(Model model) {
        List<Course> courses = coursesRepository.findAll(
                Sort.by("name").descending());
        ModelAndView modelAndView = new ModelAndView("admin/courses");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Course());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    ModelAndView addNewStudent(Model model, @ModelAttribute("course") Course course) {
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    ModelAndView viewStudents(Model model, @RequestParam final int id,
                              HttpSession session,
                              @RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("admin/course_students");
        Optional<Course> course = coursesRepository.findById(id);
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
        Person personEntity = personRepository.readByEmail(person.getEmail());

        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getCourseId()
                    + "&error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(course);
        course.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("course", course);
        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    ModelAndView deleteStudentFromCourse(Model model, @RequestParam final int personId,
                                         HttpSession session) {
        Course course = (Course) session.getAttribute("course");
        Optional<Person> person = personRepository.findById(personId);
        if (person.isPresent()) {
            person.get().getCourses().remove(course);
            course.getPersons().remove(person.get());
            personRepository.save(person.get());
        }
        session.setAttribute("course", course);
        return new ModelAndView("redirect:/admin/viewStudents?id=" + course.getCourseId());
    }
}
