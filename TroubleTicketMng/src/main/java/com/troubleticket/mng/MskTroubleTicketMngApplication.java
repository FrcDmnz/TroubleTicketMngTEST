package com.troubleticket.mng;

import com.troubleticket.mng.models.User;
import com.troubleticket.mng.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main entry point of the Spring Boot application.
 * It also handles the code-first database initialization on startup.
 */
@SpringBootApplication
public class MskTroubleTicketMngApplication {

    public static void main(String[] args) {
        SpringApplication.run(MskTroubleTicketMngApplication.class, args);
    }

    /**
     * A bean that runs automatically after the application context is loaded.
     * It checks if the database is empty and creates a default admin user with an encrypted password.
     */
    @Bean
    CommandLineRunner initializeDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Controlla se l'admin esiste già, altrimenti lo crea con la nuova struttura
            if (!userRepository.existsById("admin")) {
                
                String encryptedPassword = passwordEncoder.encode("admin123!");

                User admin = User.builder()
                        .username("admin")
                        .password(encryptedPassword)
                        .name("Amministratore di Sistema") // Nuovo campo
                        .site("Milano")
                        .role("ADMIN") // Nuovo campo
                        .email("admin@mng.com")
                        .flag("pinco.png")
                        .expirationDate(java.time.LocalDate.now().plusYears(1)) // Nuovo campo (Scade tra 1 anno)
                        .build();
                
                userRepository.save(admin);
                
                System.out.println(">>> CODE-FIRST: Database completato. Utente 'admin' aggiornato creato con successo! <<<");
            } else {
                System.out.println(">>> CODE-FIRST: L'utente admin esiste già nel database. <<<");
            }
        };
    }
}