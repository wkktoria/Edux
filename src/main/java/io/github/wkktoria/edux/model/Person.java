package io.github.wkktoria.edux.model;

import io.github.wkktoria.edux.annotation.FieldsValueMatch;
import io.github.wkktoria.edux.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match."
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match."
        )
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @NotBlank(message = "Name must not be blank.")
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;

    @NotBlank(message = "Phone number must not be blank.")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Provide a valid phone number.")
    private String phoneNumber;

    @NotBlank(message = "Email must not be blank.")
    @Email(message = "Provide a valid email address.")
    private String email;
    
    @Transient
    private String confirmEmail;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 5, message = "Password must be at least 5 characters long.")
    @PasswordValidator
    private String password;

    @Transient
    private String confirmPassword;
}
