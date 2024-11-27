package io.github.wkktoria.edux.rest.controller;

import io.github.wkktoria.edux.model.Teacher;
import io.github.wkktoria.edux.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/teacher", produces = {
        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
})
@CrossOrigin(origins = "*")
class TeacherRestController {
    private final TeacherRepository teacherRepository;

    @Autowired
    TeacherRestController(final TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/getTeachers")
    List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/getTeacherByEmail")
    Teacher getTeacherByEmail(@RequestParam final String email) {
        return teacherRepository.findByEmail(email);
    }
}
