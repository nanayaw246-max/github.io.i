package com.example.semproject.Controller;

import com.example.semproject.UserService.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.Map;
import java.util.HashMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String showOtpPage(@RequestParam(value = "username", required = false) String username, Model model) {
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username is missing for OTP verification");
        }
        model.addAttribute("username", username); // Pass the username to the OTP page
        return "otp"; // Return the OTP page (otp.html)
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam("username") String username, @RequestParam("otp") String otp, Model model) {
        System.out.println("Username: " + username); // Debug log
        System.out.println("Entered OTP: " + otp); // Debug log

        boolean isValid = userServices.verifyOtp(username, otp);
        if (!isValid) {
            model.addAttribute("error", "Invalid OTP. Please try again.");
            model.addAttribute("username", username); // Pass username back to the OTP page
            return "otp"; // Reload the OTP page with an error message
        }
        return "redirect:/home"; // Redirect to the home page if OTP is valid
    }

    private Map<String, String> otpStorage = new HashMap<>(); // Initialize OTP storage

    public boolean verifyOtp(String username, String otp) {
        String storedOtp = otpStorage.get(username);
        System.out.println("Stored OTP for " + username + ": " + storedOtp); // Debug log
        System.out.println("Entered OTP: " + otp); // Debug log
        return storedOtp != null && storedOtp.equals(otp);
    }
}