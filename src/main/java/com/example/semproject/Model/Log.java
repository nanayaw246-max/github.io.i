package com.example.semproject.Model;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Log {

    @Id
    private String id;

    @NotEmpty(message = "Username cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must contain only letters and numbers")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    // Constructors
    public Log() {}

    public Log(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
