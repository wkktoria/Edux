package io.github.wkktoria.edux.service;

import io.github.wkktoria.edux.model.EduxClass;
import io.github.wkktoria.edux.repository.EduxClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class EduxClassServiceTest {
    @Mock
    private EduxClassRepository eduxClassRepository;

    @InjectMocks
    private EduxClassService SUT;

    private EduxClass eduxClass;

    @BeforeEach
    void setUp() {
        eduxClass = new EduxClass();
        eduxClass.setClassId(1);
        eduxClass.setName("Test");
    }

    @Test
    void test_findAll_noClasses_returnsEmptyList() {
        given(eduxClassRepository.findAll()).willReturn(Collections.emptyList());

        List<EduxClass> result = SUT.findAll();

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void test_findAll_withOneClass_returnsListWithOneClass() {
        given(eduxClassRepository.findAll()).willReturn(Collections.singletonList(eduxClass));

        List<EduxClass> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(eduxClass)).isTrue();
    }

    @Test
    void test_findAll_withClasses_returnsListContainingAllClasses() {
        given(eduxClassRepository.findAll()).willReturn(List.of(new EduxClass(), new EduxClass(), new EduxClass()));

        List<EduxClass> result = SUT.findAll();

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void test_findClassWithId_roleWithProvidedIdExists_returnsOptionalOfClassWithProvidedId() {
        given(eduxClassRepository.findById(1)).willReturn(Optional.of(eduxClass));

        Optional<EduxClass> result = SUT.findClassWithId(1);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void test_findClassWithId_roleWithProvidedIdExists_optionalIsPresent() {
        given(eduxClassRepository.findById(1)).willReturn(Optional.of(eduxClass));

        Optional<EduxClass> result = SUT.findClassWithId(1);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void test_findClassWithId_roleWithProvidedIdExists_optionalOfClassIsProperEduxClass() {
        given(eduxClassRepository.findById(1)).willReturn(Optional.of(eduxClass));

        Optional<EduxClass> optional = SUT.findClassWithId(1);
        EduxClass result = optional.get();

        assertThat(result).isEqualTo(eduxClass);
    }

    @Test
    void test_saveClass_validDetails_returnsTrue() {
        given(eduxClassRepository.save(eduxClass)).willReturn(eduxClass);

        boolean result = SUT.saveClass(eduxClass);

        assertThat(result).isTrue();
    }

    @Test
    void test_saveClass_invalidDetails_returnsFalse() {
        given(eduxClassRepository.save(eduxClass)).willReturn(null);

        boolean result = SUT.saveClass(eduxClass);

        assertThat(result).isFalse();
    }

    @Test
    void test_updateClass_existentClass_returnsUpdatedEduxClass() {
        eduxClass.setName("Updated Name");
        given(eduxClassRepository.save(eduxClass)).willReturn(eduxClass);

        EduxClass result = SUT.updateClass(eduxClass);

        assertEquals("Updated Name", result.getName());
    }
}