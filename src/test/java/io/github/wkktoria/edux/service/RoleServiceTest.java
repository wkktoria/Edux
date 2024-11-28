package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.Role;
import io.github.wkktoria.edux.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService SUT;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setRoleId(1);
        role.setRoleName("TEST");
    }

    @Test
    void test_findAll_noRoles_returnsEmptyList() {
        given(roleRepository.findAll()).willReturn(Collections.emptyList());

        List<Role> result = SUT.findAll();

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void test_findAll_withOneRole_returnsListWithOneRole() {
        given(roleRepository.findAll()).willReturn(Collections.singletonList(role));

        List<Role> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(role)).isTrue();
    }

    @Test
    void test_findAll_WithRoles_returnsListContainingAllRoles() {
        given(roleRepository.findAll()).willReturn(List.of(new Role(), new Role(), new Role()));

        List<Role> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void test_findRoleWithName_roleWithProvidedNameExists_returnsRoleWithProvidedName() {
        given(roleRepository.getByRoleName("TEST")).willReturn(role);

        Role result = SUT.findRoleWithName("TEST");

        assertThat(result).isEqualTo(role);
    }

    @Test
    void test_findRoleWithName_roleWithProvidedNameDoesNotExist_returnsNull() {
        given(roleRepository.getByRoleName("nonexistent")).willReturn(null);

        Role result = SUT.findRoleWithName("nonexistent");

        assertThat(result).isNull();
    }
}