package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.Holiday;
import io.github.wkktoria.edux.repository.HolidaysRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class HolidaysServiceTest {
    @Mock
    private HolidaysRepository holidaysRepository;

    @InjectMocks
    private HolidaysService SUT;

    private Holiday holiday;

    @BeforeEach
    void setUp() {
        holiday = new Holiday();
        holiday.setDay("Test Day");
        holiday.setReason("Test");
        holiday.setType(Holiday.Type.FEDERAL);
    }

    @Test
    void test_findAll_noHolidays_returnsEmptyList() {
        given(holidaysRepository.findAll()).willReturn(Collections.emptyList());

        List<Holiday> result = SUT.findAll();

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void test_findAll_withOneHoliday_returnsListWithOneHoliday() {
        given(holidaysRepository.findAll()).willReturn(Collections.singletonList(holiday));

        List<Holiday> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(holiday)).isEqualTo(true);
    }

    @Test
    void test_findAll_withHolidays_returnsListContainingAllHolidays() {
        given(holidaysRepository.findAll()).willReturn(List.of(new Holiday(), new Holiday(), new Holiday()));

        List<Holiday> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void test_findHolidaysWithType_holidayWithTypeExists_returnsListWithHolidayOfProvidedType() {
        given(holidaysRepository.findAll()).willReturn(List.of(holiday));

        List<Holiday> result = SUT.findHolidaysWithType("federal");

        assertThat(result.contains(holiday)).isEqualTo(true);
        assertThat(result.getFirst().getType()).isEqualTo(Holiday.Type.FEDERAL);
    }
}