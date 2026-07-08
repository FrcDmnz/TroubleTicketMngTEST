package com.troubleticket.mng.dtos;

import lombok.*;

/**
 * Data Transfer Object (DTO) for capturing the login payload 
 * sent by the Angular frontend application.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * The username entered by the user in the login form.
     */
    private String username;

    /**
     * The plain text password entered by the user in the login form.
     */
    private String password;
}