package com.example.semproject.Model;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users") // Ensure this matches your MongoDB collection name
public class Sign {

    @Id
    private String id;

    @NotEmpty(message = "Full name cannot be empty")
    @Size(min = 10, max = 50, message = "Full name must be between 10 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Full name can only contain letters and spaces")
    private String full_name;

    /**
     *
     */
    @NotEmpty(message = "Email cannot be empty")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format. Please enter a valid email address (e.g., user@example.com)"
    )
    @Size(min = 10, max = 70, message = "Email must be between 10 and 70 characters")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 30 , message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
    private String password;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=_-]).*$" , message = "Username must contain only letters and numbers and special character")
    private String username;

    // Constructors, getters, and setters
    public Sign(String full_name, String email, String password, String username) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Sign() {}

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "id='" + id + '\'' +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
