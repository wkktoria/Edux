package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.Holiday;
import io.github.wkktoria.edux.repository.HolidaysRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class HolidaysService {
    private final HolidaysRepository holidaysRepository;

    @Autowired
    public HolidaysService(final HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    public List<Holiday> findAll() {
        Iterable<Holiday> holidays = holidaysRepository.findAll();
        return StreamSupport.stream(holidays.spliterator(), false).toList();
    }

    public List<Holiday> findHolidaysWithType(final String type) {
        List<Holiday> holidays = findAll();
        return holidays.stream()
                .filter(holiday -> holiday.getType().toString().equalsIgnoreCase(type))
                .toList();
    }
}
