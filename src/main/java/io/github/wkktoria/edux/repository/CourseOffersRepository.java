package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.CourseOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseOffersRepository extends JpaRepository<CourseOffer, Integer> {
    List<CourseOffer> findAllByOrderByStarsDesc();
}
