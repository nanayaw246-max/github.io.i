// filepath: d:\Documents\semproj\semproject\src\main\java\com\example\semproject\Controller\LogController.java
package com.example.semproject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.semproject.UserService.LogServices;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/login")
public class LogController {

    @Autowired
    private LogServices logServices;

    @GetMapping("/")
    public String logPage(Model model) {
        model.addAttribute("log", new com.example.semproject.Model.Log()); // Add a new Log object to the model
        return "log"; // Return the name of the log.html template
    }

    @PostMapping("/log")
    public String log(
            @Valid @ModelAttribute("log") com.example.semproject.Model.Log log, // Bind the Log object
            BindingResult bindingResult,                                        // Capture validation errors
            Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            return "log"; // Return to the login page with errors
        }

        try {
            // Validate login credentials
            logServices.validateLogin(log.getUsername(), log.getPassword());
            System.out.println("Login successful for username: " + log.getUsername());
            return "home"; // Redirect to home page on success
        } catch (RuntimeException e) {
            System.out.println("Login failed: " + e.getMessage());
            // Add error message to the model
            model.addAttribute("error", e.getMessage());
            return "log"; // Return to the login page on failure
        }
    }
}
