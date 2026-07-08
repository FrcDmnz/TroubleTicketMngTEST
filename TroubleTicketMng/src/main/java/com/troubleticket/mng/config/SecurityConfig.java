package com.troubleticket.mng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration class for Spring Security.
 * It manages password encryption, CORS settings for Angular, and HTTP routing rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines the password encoder bean using the BCrypt hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures HTTP security filter chain, protecting endpoints and handling CORS/CSRF.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Enables the custom CORS configuration defined below
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Disables CSRF protection since we are using stateless JWT authentication
            .csrf(csrf -> csrf.disable())
            // Defines authorization rules for HTTP requests
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll() // Allows anyone to access the login endpoint
                .anyRequest().authenticated() // All other requests require authentication
            );
        
        return http.build();
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) to allow requests from the Angular application.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allows requests coming specifically from your Angular development server
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        // Allows standard HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allows necessary headers for authentication and JSON communication
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Applies this rule to all endpoints
        return source;
    }
}