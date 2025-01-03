package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.service.PersonService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
class DashboardController {
    private final PersonService personService;

    @Autowired
    DashboardController(final PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/dashboard")
    String displayDashboard(Model model, Authentication auth, HttpSession session) {
        Person person = personService.findByEmail(auth.getName());
        assert person != null;
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", person.getRole().getRoleName());

        if (person.getEduxClass() != null && person.getEduxClass().getName() != null) {
            model.addAttribute("enrolledClass", person.getEduxClass().getName());
        }

        session.setAttribute("loggedInPerson", person);
        return "dashboard";
    }
}
