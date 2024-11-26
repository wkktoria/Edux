package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.Course;
import io.github.wkktoria.edux.repository.CoursesRepository;
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
class CoursesServiceTest {
    @Mock
    private CoursesRepository coursesRepository;

    @InjectMocks
    private CoursesService SUT;

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setCourseId(1);
        course.setName("Test Course");
        course.setDescription("This is a test course.");
        course.setFees("$500");
    }

    @Test
    void test_findAll_noCourses_returnsEmptyList() {
        given(coursesRepository.findAll()).willReturn(Collections.emptyList());

        List<Course> result = SUT.findAll();

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void test_findAll_withOneCourse_returnsListWithOneCourse() {
        given(coursesRepository.findAll()).willReturn(Collections.singletonList(course));

        List<Course> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(course)).isTrue();
    }

    @Test
    void test_findAll_withCourses_returnsListContainingAllCourses() {
        given(coursesRepository.findAll()).willReturn(List.of(new Course(), new Course(), new Course()));

        List<Course> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void test_findFirstN_nEqualsToOne_returnsListContainingFirstCourse() {
        given(coursesRepository.findAll()).willReturn(Collections.singletonList(course));

        List<Course> result = SUT.findFirstN(1);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(course)).isTrue();
    }
}