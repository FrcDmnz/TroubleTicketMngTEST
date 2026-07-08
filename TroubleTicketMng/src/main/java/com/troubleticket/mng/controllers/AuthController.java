package com.troubleticket.mng.controllers;

import com.troubleticket.mng.dtos.LoginRequest;
import com.troubleticket.mng.services.AuthService;
import com.troubleticket.mng.config.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller that exposes authentication endpoints for the Angular frontend.
 * It handles login HTTP POST requests and returns JWT tokens upon success.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    // Spring automatically injects the AuthService and JwtUtils components here
    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Endpoint for user login. Resolves at POST http://localhost:8080/api/auth/login
     * * @param loginRequest the DTO containing username and password from the frontend
     * @return ResponseEntity containing the JWT token if successful, or an error message if unauthorized
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Step 1: Validate credentials via the AuthService
        boolean isValid = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (isValid) {
            // Step 2: If valid, generate the actual JWT token for the user
            String token = jwtUtils.generateJwtToken(loginRequest.getUsername());
            
            // Step 3: Return HTTP 200 OK along with the token wrapped in a JSON object
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "token", token
            ));
        } else {
            // Step 4: If invalid, return HTTP 401 Unauthorized with an error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                        "status", "error", 
                        "message", "Invalid username or password"
                    ));
        }
    }
}