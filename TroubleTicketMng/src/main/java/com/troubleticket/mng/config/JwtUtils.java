package com.troubleticket.mng.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

/**
 * Utility component for handling JSON Web Tokens (JWT).
 * It manages token generation using the secret key and expiration settings.
 */
@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private Duration jwtExpiration;

    /**
     * Decodes the Base64 encoded secret key and converts it into a SecretKey object robust for HS512.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a valid JWT token signed with the robust secret key.
     * * @param username the subject of the token (the logged-in user)
     * @return a signed compact JWT string
     */
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Standard and universally supported method
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }
}