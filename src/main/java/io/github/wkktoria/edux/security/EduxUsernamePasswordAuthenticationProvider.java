package io.github.wkktoria.edux.security;

import io.github.wkktoria.edux.model.Person;
import io.github.wkktoria.edux.model.Role;
import io.github.wkktoria.edux.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EduxUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final PersonRepository personRepository;

    @Autowired
    public EduxUsernamePasswordAuthenticationProvider(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String email = authentication.getName();
        final String password = authentication.getCredentials().toString();
        Person person = personRepository.readByEmail(email);

        if (person != null && person.getPersonId() > 0 && password.equals(person.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    person.getName(), password, getGrantedAuthorities(person.getRole()));
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private List<GrantedAuthority> getGrantedAuthorities(final Role role) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
        return grantedAuthorities;
    }
}
