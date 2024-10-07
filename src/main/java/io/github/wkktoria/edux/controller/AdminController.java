package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.EduxClass;
import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.repository.EduxClassRepository;
import io.github.wkktoria.edux.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    AdminController(final EduxClassRepository eduxClassRepository, final PersonRepository personRepository) {
        this.eduxClassRepository = eduxClassRepository;
        this.personRepository = personRepository;
    }

    @RequestMapping("/displayClasses")
    ModelAndView displayClasses(Model model) {
        List<EduxClass> eduxClasses = eduxClassRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("classes");
        modelAndView.addObject("eduxClass", new EduxClass());
        modelAndView.addObject("eduxClasses", eduxClasses);
        return modelAndView;
    }

    @RequestMapping("/addNewClass")
    ModelAndView addNewClass(Model model, @ModelAttribute("eduxClass") EduxClass eduxClass) {
        eduxClassRepository.save(eduxClass);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<EduxClass> eduxClass = eduxClassRepository.findById(id);
        for (Person person : eduxClass.get().getPersons()) {
            person.setEduxClass(null);
            personRepository.save(person);
        }
        eduxClassRepository.deleteById(id);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/displayStudents")
    ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                 @RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("students");
        Optional<EduxClass> eduxClass = eduxClassRepository.findById(classId);
        modelAndView.addObject("eduxClass", eduxClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("eduxClass", eduxClass.get());

        String message;
        if (error != null) {
            message = "Invalid email address entered.";
            modelAndView.addObject("message", message);
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

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + eduxClass.getClassId());
        return modelAndView;
    }
}
