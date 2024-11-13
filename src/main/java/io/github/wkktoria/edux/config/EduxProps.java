package io.github.wkktoria.edux.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Component("eduxProps")
@Data
@ConfigurationProperties(prefix = "edux")
@Validated
public class EduxProps {
    @Min(value = 5, message = "Must be between 5 and 25.")
    @Max(value = 25, message = "Must be between 5 and 25.")
    private int pageSize;

    private Map<String, String> contact;

    private List<String> branches;
}
