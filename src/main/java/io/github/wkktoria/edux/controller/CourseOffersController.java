package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.CourseOffer;
import io.github.wkktoria.edux.service.CourseOffersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
class CourseOffersController {
    private final CourseOffersService courseOffersService;

    @Autowired
    CourseOffersController(final CourseOffersService courseOffersService) {
        this.courseOffersService = courseOffersService;
    }

    @GetMapping("/courses")
    ModelAndView displayCourseOffers() {
        List<CourseOffer> courseOffers = courseOffersService.findAll();
        ModelAndView modelAndView = new ModelAndView("courses");
        modelAndView.addObject("courseOffers", courseOffers);
        return modelAndView;
    }
}
