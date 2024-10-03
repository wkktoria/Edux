package io.github.wkktoria.edux.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "contact_message")
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;

    @NotBlank(message = "Name must not be blank.")
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;

    @NotBlank(message = "Phone number must not be blank.")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Provide a valid phone number.")
    private String phoneNumber;

    @NotBlank(message = "Email must not be blank.")
    @Email(message = "Provide a valid email address.")
    private String email;

    @NotBlank(message = "Subject must not be blank.")
    @Size(min = 5, message = "Subject must be at least 5 characters long.")
    private String subject;

    @NotBlank(message = "Message must not be blank.")
    @Size(min = 10, message = "Message must be at least 10 characters long.")
    private String message;

    private String status;
}
