package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.config.EduxProps;
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
import java.util.Optional;

@Slf4j
@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final EduxProps eduxProps;

    @Autowired
    public ContactService(final ContactRepository contactRepository, final EduxProps eduxProps) {
        this.contactRepository = contactRepository;
        this.eduxProps = eduxProps;
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

    public List<Contact> findMessagesWithStatus(final String status) {
        return contactRepository.findByStatus(status);
    }

    public List<Contact> findMessagesWithOpenStatus() {
        return findMessagesWithStatus(EduxConstants.OPEN);
    }

    public Page<Contact> findMessagesWithOpenStatus(final int pageNum, final String sortField, final String sortDir) {
        final int pageSize = Integer.parseInt(eduxProps.getContact().get("pageSize"));
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return contactRepository.findByStatusPageable(EduxConstants.OPEN, pageable);
    }

    public Optional<Contact> findMessageById(final int contactId) {
        return contactRepository.findById(contactId);
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

    public void deleteMessageDetails(final int contactId) {
        contactRepository.deleteById(contactId);
    }
}
