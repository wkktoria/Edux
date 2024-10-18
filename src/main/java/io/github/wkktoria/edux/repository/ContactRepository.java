package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    @Query(value = "SELECT * FROM contact_message c WHERE c.status = :status", nativeQuery = true)
    List<Contact> findByStatus(final String status);

    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    Page<Contact> findByStatus(final String status, final Pageable pageable);
}
