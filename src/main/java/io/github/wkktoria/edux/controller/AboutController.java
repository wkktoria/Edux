package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Teacher;
import io.github.wkktoria.edux.repository.TeacherRepository;
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
    private final TeacherRepository teacherRepository;

    @Autowired
    AboutController(final TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    ModelAndView getTeachersInfo() {
        List<Teacher> teachers = teacherRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("about");
        modelAndView.addObject("teachers", teachers);
        return modelAndView;

    }
}
