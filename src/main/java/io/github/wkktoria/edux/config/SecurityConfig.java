package io.github.wkktoria.edux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/saveMessage")
                        .ignoringRequestMatchers("/public/**")
                        .ignoringRequestMatchers("/api/**")
                        .ignoringRequestMatchers("/data-api/**")
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/saveMessage").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/displayProfile").authenticated()
                        .requestMatchers("/updateProfile").authenticated()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/data-api/**").authenticated()
                        .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                        .requestMatchers("/closeMessage/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
