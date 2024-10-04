package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getByRoleName(final String roleName);
}
