package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("public")
class PublicController {
    private final PersonService personService;

    @Autowired
    PublicController(final PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String displayRegisterPage(Model model) {
        model.addAttribute("person", new Person());
        return "register";
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    String createUser(@Valid @ModelAttribute("person") Person person, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }

        boolean isSaved = personService.createNewPerson(person);

        if (isSaved) {
            return "redirect:/login?register=true";
        } else {
            return "register";
        }
    }
}
