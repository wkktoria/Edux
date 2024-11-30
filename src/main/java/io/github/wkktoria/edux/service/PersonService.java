package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.constants.EduxConstants;
import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.model.Role;
import io.github.wkktoria.edux.repository.PersonRepository;
import io.github.wkktoria.edux.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(final PersonRepository personRepository,
                         final RoleRepository roleRepository,
                         final PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person findByEmail(final String email) {
        return personRepository.readByEmail(email);
    }

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        Role role = roleRepository.getByRoleName(EduxConstants.STUDENT_ROLE);
        person.setRole(role);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person = personRepository.save(person);

        if (person != null && person.getPersonId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }

    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    public Person findPersonWithEmail(final String email) {
        return personRepository.readByEmail(email);
    }

    public Optional<Person> findPersonWithId(final int personId) {
        return personRepository.findById(personId);
    }
}
