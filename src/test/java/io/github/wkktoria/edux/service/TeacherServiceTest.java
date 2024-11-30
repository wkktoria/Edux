package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.Teacher;
import io.github.wkktoria.edux.repository.TeacherRepository;
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
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService SUT;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setTeacherId(1);
        teacher.setFirstName("Test Teacher");
        teacher.setLastName("Test Teacher");
        teacher.setEmail("teacher@test.com");
        teacher.setSummary("This is a test teacher.");
    }

    @Test
    void test_findAll_noTeachers_returnsEmptyList() {
        given(teacherRepository.findAll()).willReturn(Collections.emptyList());

        List<Teacher> result = SUT.findAll();

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void test_findAll_withOneTeacher_returnsListWithOneTeacher() {
        given(teacherRepository.findAll()).willReturn(Collections.singletonList(teacher));

        List<Teacher> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst()).isEqualTo(teacher);
    }

    @Test
    void test_findAll_withTeachers_returnsListContainingAllTeachers() {
        given(teacherRepository.findAll()).willReturn(List.of(new Teacher(), new Teacher(), new Teacher()));

        List<Teacher> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void test_findTeacherWithEmail_teacherWithEmailExists_returnsTeacherWithProperEmail() {
        given(teacherRepository.findByEmail("teacher@test.com")).willReturn(teacher);

        Teacher result = SUT.findTeacherWithEmail("teacher@test.com");

        assertThat(result).isEqualTo(teacher);
        assertThat(result.getEmail()).isEqualTo("teacher@test.com");
    }

    @Test
    void test_findTeacherWithEmail_teacherWithEmailDoesNotExist_returnsNull() {
        given(teacherRepository.findByEmail("nonexistant@email.com")).willReturn(null);


        Teacher result = SUT.findTeacherWithEmail("nonexistant@email.com");

        assertThat(result).isNull();
    }
}