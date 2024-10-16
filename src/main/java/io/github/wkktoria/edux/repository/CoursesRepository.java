package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Integer> {
    List<Course> findByOrderByNameDesc();

    List<Course> findByOrderByName();
}
