package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.EduxClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EduxClassRepository extends JpaRepository<EduxClass, Integer> {
}
