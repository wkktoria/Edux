package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.constants.EduxConstants;
import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.model.Role;
import io.github.wkktoria.edux.repository.PersonRepository;
import io.github.wkktoria.edux.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
