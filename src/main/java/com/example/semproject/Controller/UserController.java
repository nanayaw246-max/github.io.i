// Purpose: Controller for User Services.
// Team: 64
package com.example.semproject.Controller;

import com.example.semproject.UserService.UserServices;
import com.example.semproject.Model.Sign;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sign", new Sign()); // Add a new Sign object to the model
        return "sign";
    }

    @PostMapping("/add")
    public String addUser(
            @Valid @ModelAttribute("sign") Sign sign, // Bind the Sign object
            BindingResult bindingResult,              // Capture validation errors
            Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Validation error: " + error.getDefaultMessage());
            });
            return "sign"; // Return to the sign-up page with errors
        }

        try {
            System.out.println("Attempting to save user: " + sign.getUsername());
            userServices.addUser(sign); // Save the user and send OTP
            model.addAttribute("username", sign.getUsername()); // Pass username to OTP page
            return "redirect:/otp/?username=" + sign.getUsername(); // Redirect to OTP verification page
        } catch (RuntimeException e) {
            System.out.println("Error saving user: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "sign"; // Return to the sign-up page
        }
    }
}
