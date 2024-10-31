package io.github.wkktoria.edux.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.wkktoria.edux.annotation.FieldsValueMatch;
import io.github.wkktoria.edux.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * This represents the user of the app.
 * The better name would be USER.
 */
@Getter
@Setter
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
public class Person extends BaseEntity {
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
    @JsonIgnore
    private String confirmEmail;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 5, message = "Password must be at least 5 characters long.")
    @PasswordValidator
    @JsonIgnore
    private String password;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", referencedColumnName = "classId")
    private EduxClass eduxClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_course",
            joinColumns = {
                    @JoinColumn(name = "person_id", referencedColumnName = "personId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
            })
    private Set<Course> courses = new HashSet<>();
}
