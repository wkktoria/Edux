package io.github.wkktoria.edux;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController {
    @RequestMapping(value = {"", "/", "/home"})
    String displayHomePage(Model model) {
        model.addAttribute("username", "Anna");
        return "home.html";
    }
}
