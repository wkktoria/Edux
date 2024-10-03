package io.github.wkktoria.edux.validation;

import io.github.wkktoria.edux.annotation.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {
    private List<String> weakPasswords;

    @Override
    public void initialize(final PasswordValidator constraintAnnotation) {
        weakPasswords = Arrays.asList("12345", "password", "qwerty");
    }

    @Override
    public boolean isValid(final String passwordField, final ConstraintValidatorContext context) {
        return passwordField != null && !weakPasswords.contains(passwordField);
    }
}
