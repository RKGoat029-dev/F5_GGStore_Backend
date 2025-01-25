package com.example.ecommerce_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters.")
    private String username;

    @NotBlank
    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public User(String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;

    }

}