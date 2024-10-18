package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.constants.EduxConstants;
import io.github.wkktoria.edux.model.Contact;
import io.github.wkktoria.edux.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

        final Contact savedContact = contactRepository.save(contact);

        if (savedContact != null && savedContact.getContactId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }

    public List<Contact> findMessagesWithOpenStatus() {
        return contactRepository.findByStatus(EduxConstants.OPEN);
    }

    public Page<Contact> findMessagesWithOpenStatus(final int pageNum, final String sortField, final String sortDir) {
        final int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return contactRepository.findByStatus(EduxConstants.OPEN, pageable);
    }

    public boolean updateMessageStatus(final int contactId) {
        boolean isUpdated = false;
        int result = contactRepository.updateStatusById(
                EduxConstants.CLOSED, contactId
        );

        if (result > 0) {
            isUpdated = true;
        }

        return isUpdated;
    }
}
