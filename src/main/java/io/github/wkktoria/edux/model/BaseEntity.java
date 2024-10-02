package io.github.wkktoria.edux.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class BaseEntity {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
