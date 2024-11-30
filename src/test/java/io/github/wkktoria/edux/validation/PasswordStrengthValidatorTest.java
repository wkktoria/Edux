package io.github.wkktoria.edux.validation;

import io.github.wkktoria.edux.annotation.PasswordValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
class PasswordStrengthValidatorTest {
    private final PasswordStrengthValidator SUT = new PasswordStrengthValidator();
    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private PasswordValidator annotation;

    @BeforeEach
    void setUp() {
        SUT.initialize(annotation);
    }

    @Test
    void test_isValid_weakPassword_returnsFalse() {
        final String weakPassword = "12345";

        boolean result = SUT.isValid(weakPassword, context);

        assertFalse(result);
    }

    @Test
    void test_isValid_strongPassword_returnsTrue() {
        final String strongPassword = "StrOngP@s4w0rd";

        boolean result = SUT.isValid(strongPassword, context);

        assertTrue(result);
    }
}