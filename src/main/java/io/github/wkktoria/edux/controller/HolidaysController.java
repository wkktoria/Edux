package io.github.wkktoria.edux.controller;

import io.github.wkktoria.edux.model.Holiday;
import io.github.wkktoria.edux.service.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
class HolidaysController {
    private final HolidaysService holidaysService;

    @Autowired
    public HolidaysController(final HolidaysService holidaysService) {
        this.holidaysService = holidaysService;
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

        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(), holidaysService.findHolidaysWithType(type.toString()));
        }

        return "holidays";
    }
}
