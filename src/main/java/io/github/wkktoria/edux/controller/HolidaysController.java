package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Holiday;
import io.github.wkktoria.edux.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
class HolidaysController {
    private final HolidaysRepository holidaysRepository;

    @Autowired
    public HolidaysController(final HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    @GetMapping("/holidays")
    String displayHolidays() {
        return "redirect:/holidays/all";
    }

    @GetMapping("/holidays/{display}")
    String displayHolidays(@PathVariable String display, Model model) {
        if (display != null && display.equalsIgnoreCase("all")) {
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        } else if (display != null && display.equalsIgnoreCase("festival")) {
            model.addAttribute("festival", true);
        } else if (display != null && display.equalsIgnoreCase("federal")) {
            model.addAttribute("federal", true);
        }

        Iterable<Holiday> holidays = holidaysRepository.findAll();
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(), false).toList();

        Holiday.Type[] types = Holiday.Type.values();

        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }

        return "holidays";
    }
}
