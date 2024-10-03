package io.github.wkktoria.edux.annotation;

import io.github.wkktoria.edux.validation.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {
    String message() default "Password is too weak.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
