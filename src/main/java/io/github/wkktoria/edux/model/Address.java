package io.github.wkktoria.edux.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int addressId;

    @NotBlank(message = "Address must not be blank.")
    @Size(min = 5, message = "Address must be at least 5 characters long.")
    private String address1;

    private String address2;

    @NotBlank(message = "City must not be blank.")
    @Size(min = 5, message = "City must be at least 5 characters long.")
    private String city;

    private String state;

    @NotBlank(message = "Zip code must not be blank.")
    @Pattern(regexp = "^$|[0-9]{5}", message = "Provide a valid zip code.")
    private String zipCode;

    @NotBlank(message = "Country must not be blank.")
    private String country;
}
