package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Course;
import io.github.wkktoria.edux.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
class CoursesController {
    private final CoursesService coursesService;

    @Autowired
    CoursesController(final CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/courses")
    ModelAndView displayCourses() {
        List<Course> courses = coursesService.findAll();
        ModelAndView modelAndView = new ModelAndView("courses");
        modelAndView.addObject("courses", courses);
        return modelAndView;

    }
}
