package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.config.EduxProps;
import io.github.wkktoria.edux.model.Course;
import io.github.wkktoria.edux.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {
    private final CoursesRepository coursesRepository;
    private final EduxProps eduxProps;

    @Autowired
    public CoursesService(final CoursesRepository coursesRepository, final EduxProps eduxProps) {
        this.coursesRepository = coursesRepository;
        this.eduxProps = eduxProps;
    }

    public Page<Course> findAll(final int pageNum, final String sortField, final String sortDir) {
        final int pageSize = eduxProps.getPageSize();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        return coursesRepository.findAll(pageable);
    }

    public List<Course> findAll() {
        return coursesRepository.findAll();
    }

    public List<Course> findFirstN(final int n) {
        return coursesRepository.findAll()
                .stream().limit(n).toList();
    }
}
