package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.CourseOffer;
import io.github.wkktoria.edux.service.CourseOffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
class HomeController {
    private final CourseOffersService courseOffersService;

    @Autowired
    HomeController(final CourseOffersService courseOffersService) {
        this.courseOffersService = courseOffersService;
    }

    @RequestMapping(value = {"", "/", "/home"})
    ModelAndView displayHomePage() {
        List<CourseOffer> topCourses = courseOffersService.findTopTree();
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("topCourses", topCourses);

        return modelAndView;
    }
}
