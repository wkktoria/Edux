package io.github.wkktoria.edux.model;

import lombok.Data;

@Data
public class Contact {
    private String name;
    private String phoneNumber;
    private String email;
    private String subject;
    private String message;
}
