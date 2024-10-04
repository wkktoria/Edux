package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.constants.EduxConstants;
import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.model.Role;
import io.github.wkktoria.edux.repository.PersonRepository;
import io.github.wkktoria.edux.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public PersonService(final PersonRepository personRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        Role role = roleRepository.getByRoleName(EduxConstants.STUDENT_ROLE);
        person.setRole(role);
        person = personRepository.save(person);

        if (person != null && person.getPersonId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }
}
