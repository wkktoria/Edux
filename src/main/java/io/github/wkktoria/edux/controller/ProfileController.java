package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Address;
import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.model.Profile;
import io.github.wkktoria.edux.service.PersonService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller("profileControllerBean")
class ProfileController {
    private final PersonService personService;

    @Autowired
    ProfileController(final PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/displayProfile")
    ModelAndView displayProfile(Model model, HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setPhoneNumber(person.getPhoneNumber());
        profile.setEmail(person.getEmail());

        Address address = person.getAddress();
        if (address != null && address.getAddressId() > 0) {
            profile.setAddress1(address.getAddress1());
            profile.setAddress2(address.getAddress2());
            profile.setCity(address.getCity());
            profile.setState(address.getState());
            profile.setZipCode(address.getZipCode());
            profile.setCountry(address.getCountry());
        }

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping(value = "/updateProfile")
    String updateProfile(@Valid @ModelAttribute("profile") Profile profile,
                         Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "profile";
        }

        Person person = (Person) session.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setPhoneNumber(profile.getPhoneNumber());
        person.setEmail(profile.getEmail());
        if (person.getAddress() == null || !(person.getAddress().getAddressId() > 0)) {
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        person.getAddress().setCountry(profile.getCountry());

        personService.updatePerson(person);
        session.setAttribute("loggedInPerson", person);

        return "redirect:/displayProfile";
    }
}
