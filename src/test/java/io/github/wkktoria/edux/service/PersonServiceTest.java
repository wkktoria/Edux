package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.repository.PersonRepository;
import io.github.wkktoria.edux.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PersonService SUT;

    private Person person;
    private Person invalidPerson;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setPersonId(1);
        person.setName("John Doe");
        person.setPhoneNumber("+11123456789");
        person.setEmail("john.doe@example.com");
        person.setConfirmEmail("john.doe@example.com");
        person.setPassword("Str0ngP@ssw0rd");
        person.setConfirmPassword("Str0ngP@ssw0rd");

        invalidPerson = new Person();
    }

    @Test
    void test_createNewPerson_validInfo_returnsTrue() {
        given(personRepository.save(person)).willReturn(person);

        boolean result = SUT.createNewPerson(person);

        assertTrue(result);
    }

    @Test
    void test_createNewPerson_invalidInfo_returnsFalse() {
        given(personRepository.save(invalidPerson)).willReturn(null);

        boolean result = SUT.createNewPerson(invalidPerson);

        assertFalse(result);
    }
}