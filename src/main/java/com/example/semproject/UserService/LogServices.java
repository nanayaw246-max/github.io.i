package com.example.semproject.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.semproject.Model.Log;
import com.example.semproject.Repository.LogRepo;

@Service
public class LogServices {

    @Autowired
    private LogRepo logRepo;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    public void validateLogin(String username, String password) {
        System.out.println("Checking username: " + username);

        // Check if the username exists in the database
        Log log = logRepo.findByUsername(username);
        if (log == null) {
            System.out.println("Username not found in the database");
            throw new RuntimeException("Username does not exist");
        }

        System.out.println("Username found: " + log.getUsername());

        // Check if the password matches
        if (!passwordEncoder.matches(password, log.getPassword())) {
            System.out.println("Password does not match for username: " + username);
            throw new RuntimeException("Invalid password");
        }

        System.out.println("Login successful for username: " + username);
    }
}