package io.github.wkktoria.edux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @RequestMapping("/contact")
    String displayContactPage() {
        return "contact.html";
    }

    @RequestMapping(value = "/saveMessage", method = RequestMethod.POST)
    ModelAndView saveMessage(@RequestParam String name, @RequestParam String phoneNumber,
                             @RequestParam String email, @RequestParam String subject, @RequestParam String message) {
        logger.info("Name: {}", name);
        logger.info("Phone number: {}", phoneNumber);
        logger.info("Email: {}", email);
        logger.info("Subject: {}", subject);
        logger.info("Message: {}", message);
        return new ModelAndView("redirect:/contact");
    }
}
