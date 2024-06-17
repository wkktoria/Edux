package io.github.wkktoria.edux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class ContactController {
    @RequestMapping("/contact")
    String displayContactPage() {
        return "contact.html";
    }
}
