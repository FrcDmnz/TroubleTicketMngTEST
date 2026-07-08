package com.troubleticket.mng.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "Username", length = 50) 
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;
    
    @Column(name = "Name", length = 100)
    private String name;
    
    @Column(name = "Site", length = 50)
    private String site;
    
    @Column(name = "Role", length = 20)
    private String role;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "Flag", length = 100)
    private String flag;
    
    @Column(name = "ExpirationDate")
    private LocalDate expirationDate;
}