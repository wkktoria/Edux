package io.github.wkktoria.edux.rest.controller;

import io.github.wkktoria.edux.model.Role;
import io.github.wkktoria.edux.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/role", produces = {
        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
})
@CrossOrigin(origins = "*")
class RoleRestController {
    private final RoleService roleService;

    @Autowired
    RoleRestController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getRoles")
    List<Role> getRoles() {
        return roleService.findAll();
    }

    @GetMapping("/getRoleByName")
    Role getRoleByName(final String name) {
        return roleService.findRoleWithName(name);
    }
}
