package io.github.wkktoria.edux;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController {
    @RequestMapping(value = {"", "/", "/home"})
    String displayHomePage() {
        return "home.html";
    }
}
