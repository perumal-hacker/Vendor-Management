package com.vendor.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security.
 * <p>
 * This class defines security-related beans such as password encoding
 * and HTTP security configuration.
 * </p>
 */
@Configuration
public class SecurityConfig {

    /**
     * Creates and provides a {@link PasswordEncoder} bean.
     * <p>
     * This implementation uses {@link BCryptPasswordEncoder} to securely hash passwords.
     * BCrypt is a strong hashing algorithm recommended for storing passwords.
     * </p>
     *
     * @return a BCrypt-based password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * <p>
     * This configuration:
     * <ul>
     *     <li>Disables CSRF protection</li>
     *     <li>Allows all incoming requests without authentication</li>
     * </ul>
     * </p>
     *
     * <p><b>Note:</b> This setup is suitable for development or testing purposes only.
     * It is not recommended for production environments.</p>
     *
     * @param http the {@link HttpSecurity} object used to configure security
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if any error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all APIs
                );

        return http.build();
    }
}