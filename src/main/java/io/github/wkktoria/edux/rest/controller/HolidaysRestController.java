package io.github.wkktoria.edux.rest.controller;

import io.github.wkktoria.edux.model.Holiday;
import io.github.wkktoria.edux.repository.HolidaysRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping(path = "/api/holidays", produces = {
        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
})
@CrossOrigin(origins = "*")
class HolidaysRestController {
    private final HolidaysRepository holidaysRepository;

    @Autowired
    HolidaysRestController(final HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    @GetMapping("/getHolidays")
    List<Holiday> getHolidays() {
        Iterable<Holiday> holidays = holidaysRepository.findAll();
        return StreamSupport.stream(holidays.spliterator(), false).toList();
    }

    @GetMapping("/getHolidaysByType")
    List<Holiday> getHolidaysByType(@RequestParam final String type) {
        Iterable<Holiday> holidays = holidaysRepository.findAll();
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(), false).toList();

        return holidayList.stream().filter(holiday ->
                holiday.getType().toString().equalsIgnoreCase(type)).collect(Collectors.toList());
    }
}
