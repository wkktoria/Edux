package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.config.EduxProps;
import io.github.wkktoria.edux.model.Course;
import io.github.wkktoria.edux.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
class HomeController {
    private final CoursesService coursesService;
    private final EduxProps eduxProps;

    @Autowired
    HomeController(final CoursesService coursesService, final EduxProps eduxProps) {
        this.coursesService = coursesService;
        this.eduxProps = eduxProps;
    }

    @RequestMapping(value = {"", "/", "/home"})
    ModelAndView displayHomePage() {
        List<Course> topCourses = coursesService.findFirstN(4);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("topCourses", topCourses);
        modelAndView.addObject("branches", eduxProps.getBranches());

        return modelAndView;
    }
}
