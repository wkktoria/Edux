package io.github.wkktoria.edux.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "class")
public class EduxClass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;

    @NotBlank(message = "Class name must not be blank.")
    @Size(min = 3, message = "Class name must be at least 3 characters long.")
    private String name;

    @OneToMany(mappedBy = "eduxClass", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST, targetEntity = Person.class)
    private Set<Person> persons;
}
