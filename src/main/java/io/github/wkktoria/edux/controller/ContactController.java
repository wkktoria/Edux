package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.config.EduxProps;
import io.github.wkktoria.edux.model.Contact;
import io.github.wkktoria.edux.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
class ContactController {
    private final ContactService contactService;
    private final EduxProps eduxProps;

    @Autowired
    ContactController(final ContactService contactService, final EduxProps eduxProps) {
        this.contactService = contactService;
        this.eduxProps = eduxProps;
    }

    @RequestMapping("/contact")
    String displayContactPage(@RequestParam(value = "success", required = false) String success, Model model) {
        model.addAttribute("contact", new Contact());

        if (success != null) {
            model.addAttribute("message", eduxProps.getContact().get("successMessage"));
        }

        return "contact";
    }

    @RequestMapping(value = "/saveMessage", method = RequestMethod.POST)
    String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Contact form validation failed due to: {}", errors.getAllErrors());
            return "contact";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact?success=true";
    }

    @RequestMapping("/displayMessages/page/{pageNum}")
    ModelAndView displayMessages(Model model,
                                 @PathVariable(name = "pageNum") final int pageNum,
                                 @RequestParam(name = "sortField") final String sortField,
                                 @RequestParam(name = "sortDir") final String sortDir) {
        Page<Contact> messagesPage = contactService.findMessagesWithOpenStatus(pageNum, sortField, sortDir);
        List<Contact> contactMessages = messagesPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages");
        modelAndView.addObject("contactMessages", contactMessages);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", messagesPage.getTotalPages());
        model.addAttribute("totalMessages", messagesPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return modelAndView;
    }

    @RequestMapping(value = "/closeMessage", method = RequestMethod.GET)
    String closeMessage(@RequestParam final int id) {
        contactService.updateMessageStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=asc";
    }
}
