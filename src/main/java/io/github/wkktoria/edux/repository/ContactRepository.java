package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    @Query(value = "SELECT * FROM contact_message c WHERE c.status = :status", nativeQuery = true)
    List<Contact> findByStatus(final String status);

    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    Page<Contact> findByStatusPageable(final String status, final Pageable pageable);

    Page<Contact> findOpenMessages(final Pageable pageable);

    /**
     * When using this method, sorting won't work.
     *
     * @param pageable
     * @return {@link Pageable}
     */
    @Query(nativeQuery = true)
    Page<Contact> findOpenMessagesNative(final Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(final String status, final int id);
}
