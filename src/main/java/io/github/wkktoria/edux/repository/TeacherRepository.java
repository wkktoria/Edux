package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByEmail(final String email);
}
