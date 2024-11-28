package io.github.wkktoria.edux.rest.controller;

import io.github.wkktoria.edux.model.Holiday;
import io.github.wkktoria.edux.service.HolidaysService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/holidays", produces = {
        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
})
@CrossOrigin(origins = "*")
class HolidaysRestController {
    private final HolidaysService holidaysService;

    @Autowired
    HolidaysRestController(final HolidaysService holidaysService) {
        this.holidaysService = holidaysService;
    }

    @GetMapping("/getHolidays")
    List<Holiday> getHolidays() {
        return holidaysService.findAll();
    }

    @GetMapping("/getHolidaysByType")
    List<Holiday> getHolidaysByType(@RequestParam final String type) {
        return holidaysService.findHolidaysWithType(type);
    }
}
