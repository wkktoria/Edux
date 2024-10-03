package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.constants.EduxConstants;
import io.github.wkktoria.edux.model.Contact;
import io.github.wkktoria.edux.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;

        contact.setStatus(EduxConstants.OPEN);
        contact.setCreatedBy(EduxConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());

        final Contact savedContact = contactRepository.save(contact);

        if (savedContact != null && savedContact.getContactId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }

    public List<Contact> findMessagesWithOpenStatus() {
        return contactRepository.findByStatus(EduxConstants.OPEN);
    }

    public boolean updateMessageStatus(final int contactId, final String updatedBy) {
        boolean isUpdated = false;

        final Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(c -> {
            c.setStatus(EduxConstants.CLOSED);
            c.setUpdatedBy(updatedBy);
            c.setUpdatedAt(LocalDateTime.now());
        });

        final Contact updatedContact = contactRepository.save(contact.get());

        if (updatedContact != null && updatedContact.getUpdatedBy() != null) {
            isUpdated = true;
        }

        return isUpdated;
    }
}
