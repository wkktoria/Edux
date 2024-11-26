package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.constants.EduxConstants;
import io.github.wkktoria.edux.model.Contact;
import io.github.wkktoria.edux.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ContactServiceTest {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService SUT;

    private Contact contact;
    private Contact invalidContact;

    @BeforeEach
    void setUp() {
        contact = new Contact();
        contact.setContactId(1);
        contact.setName("Test Name");
        contact.setPhoneNumber("+11123456789");
        contact.setEmail("test@email.com");
        contact.setSubject("Test Subject");
        contact.setMessage("This is just a test message.");

        invalidContact = new Contact();
    }

    @Test
    void test_saveMessageDetails_validDetails_returnsTrue() {
        given(contactRepository.save(contact)).willReturn(contact);

        boolean result = SUT.saveMessageDetails(contact);

        assertTrue(result);
    }

    @Test
    void test_saveMessageDetails_invalidDetails_returnsFalse() {
        given(contactRepository.save(invalidContact)).willReturn(null);

        boolean result = SUT.saveMessageDetails(invalidContact);

        assertFalse(result);
    }

    @Test
    void test_updateMessageStatus_existentContact_returnsTrue() {
        given(contactRepository.updateStatusById(EduxConstants.CLOSED, contact.getContactId())).willReturn(1);

        boolean result = SUT.updateMessageStatus(contact.getContactId());

        assertTrue(result);
    }

    @Test
    void test_updateMessageStatus_nonExistentContact_returnsFalse() {
        given(contactRepository.updateStatusById(EduxConstants.CLOSED, invalidContact.getContactId())).willReturn(-1);

        boolean result = SUT.updateMessageStatus(invalidContact.getContactId());

        assertFalse(result);
    }
}