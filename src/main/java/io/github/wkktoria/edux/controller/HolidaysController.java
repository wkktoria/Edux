package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Holiday;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
class HolidaysController {
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

        List<Holiday> holidays = Arrays.asList(
                new Holiday("Jan 1", "New Year's Day", Holiday.Type.FESTIVAL),
                new Holiday("Oct 31", "Halloween", Holiday.Type.FESTIVAL),
                new Holiday("Nov 24", "Thanksgiving Day", Holiday.Type.FESTIVAL),
                new Holiday("Dec 25", "Christmas", Holiday.Type.FESTIVAL),
                new Holiday("Jul 4", "Independence Day", Holiday.Type.FEDERAL),
                new Holiday("Nov 11", "Veterans Day", Holiday.Type.FEDERAL)
        );

        Holiday.Type[] types = Holiday.Type.values();

        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(), (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }

        return "holidays.html";
    }
}
