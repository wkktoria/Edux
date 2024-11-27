package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Teacher;
import io.github.wkktoria.edux.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/about")
class AboutController {
    private final TeacherService teacherService;

    @Autowired
    AboutController(final TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    ModelAndView getTeachersInfo() {
        List<Teacher> teachers = teacherService.findAll();
        ModelAndView modelAndView = new ModelAndView("about");
        modelAndView.addObject("teachers", teachers);
        return modelAndView;

    }
}
