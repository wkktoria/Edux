package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path = "courses")
public interface CoursesRepository extends JpaRepository<Course, Integer> {
    List<Course> findByOrderByNameDesc();

    List<Course> findByOrderByName();

    Page<Course> findAll(final Pageable pageable);
}
