package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    List<Contact> findByStatus(final String status);
}
