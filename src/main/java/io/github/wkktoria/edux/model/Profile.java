package io.github.wkktoria.edux.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Profile {
    @NotBlank(message = "Name must not be blank.")
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;

    @NotBlank(message = "Phone number must not be blank.")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Provide a valid phone number.")
    private String phoneNumber;

    @NotBlank(message = "Email must not be blank.")
    @Email(message = "Provide a valid email address.")
    private String email;

    @NotBlank(message = "Address must not be blank.")
    @Size(min = 5, message = "Address must be at least 5 characters long.")
    private String address1;

    private String address2;

    @NotBlank(message = "City must not be blank.")
    @Size(min = 5, message = "City must be at least 5 characters long.")
    private String city;

    private String state;

    @NotBlank(message = "Zip code must not be blank.")
    @Pattern(regexp = "^[a-z0-9][a-z0-9\\- ]{0,10}[a-z0-9]$", message = "Provide a valid zip code.")
    private String zipCode;

    @NotBlank(message = "Country must not be blank.")
    private String country;
}
