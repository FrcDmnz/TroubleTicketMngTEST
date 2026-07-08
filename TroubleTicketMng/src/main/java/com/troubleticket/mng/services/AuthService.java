package com.troubleticket.mng.services;

import com.troubleticket.mng.models.User;
import com.troubleticket.mng.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service class handling the core authentication business logic.
 * It verifies user credentials by interacting with the database and checking password hashes.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring automatically injects the UserRepository and the BCryptPasswordEncoder here
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user by checking if the username exists and the password matches.
     * * @param username the username provided in the login form
     * @param plainPassword the plain text password provided in the login form
     * @return true if credentials are valid, false otherwise
     */
    public boolean authenticate(String username, String plainPassword) {
        // Step 1: Look up the user in the database using the repository
        Optional<User> userOpt = userRepository.findById(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Step 2: Use BCrypt to securely compare the raw password with the encrypted hash from the DB
            return passwordEncoder.matches(plainPassword, user.getPassword());
        }
        
        // Return false if the user was not found
        return false;
    }
}