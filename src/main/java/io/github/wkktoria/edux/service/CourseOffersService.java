package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.CourseOffer;
import io.github.wkktoria.edux.repository.CourseOffersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseOffersService {
    private final CourseOffersRepository courseOffersRepository;

    @Autowired
    public CourseOffersService(final CourseOffersRepository courseOffersRepository) {
        this.courseOffersRepository = courseOffersRepository;
    }

    public List<CourseOffer> findTopTree() {
        return courseOffersRepository.findAllByOrderByStarsDesc()
                .stream().limit(3).toList();
    }

    public List<CourseOffer> findAll() {
        return courseOffersRepository.findAll();
    }
}
