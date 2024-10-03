package io.github.wkktoria.edux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("io.github.wkktoria.edux.repository")
@EntityScan("io.github.wkktoria.edux.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class EduxApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduxApplication.class, args);
    }

}
