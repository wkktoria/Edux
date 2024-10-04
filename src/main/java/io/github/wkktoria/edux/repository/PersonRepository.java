package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PersonRepository extends JpaRepository<Person, Integer> {
}
