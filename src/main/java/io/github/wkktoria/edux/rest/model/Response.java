package io.github.wkktoria.edux.rest.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Response {
    private String statusCode;
    private String statusMessage;
}
