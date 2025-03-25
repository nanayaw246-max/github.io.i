package com.example.semproject.UserService;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.semproject.Model.Sign;
import com.example.semproject.Repository.UserRepo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServices {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpStorage = new HashMap<>(); // Store OTPs temporarily

    public void addUser(Sign sign) {
        StringBuilder errors = new StringBuilder();

        // Debug log for username
        System.out.println("Received username: " + sign.getUsername());

        // Check if username already exists
        if (repo.existsByUsername(sign.getUsername())) {
            errors.append("Username already exists. ");
        }

        // Check if email already exists
        if (repo.existsByEmail(sign.getEmail())) {
            errors.append("Email already exists. ");
        }

        // If there are any errors, throw an exception with all error messages
        if (errors.length() > 0) {
            throw new RuntimeException(errors.toString().trim());
        }

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(sign.getPassword());
        sign.setPassword(hashedPassword);

        // Debug log for PasswordEncoder class
        System.out.println("PasswordEncoder class: " + passwordEncoder.getClass().getName());

        // Save the user
        System.out.println("Saving user to the database: " + sign.getUsername());
        repo.save(sign);

        // Generate and send OTP
        String otp = generateOtp();
        otpStorage.put(sign.getUsername(), otp);
        System.out.println("Generated OTP for " + sign.getUsername() + ": " + otp); // Debug log
        sendOtpEmail(sign.getEmail(), otp);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    private void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ja5504424@gmail.com"); // Set the "from" email address
            helper.setTo(email);
            helper.setSubject("Your OTP for Verification");
            helper.setText("Your OTP is: " + otp);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }

    public boolean verifyOtp(String username, String otp) {
        String storedOtp = otpStorage.get(username.trim().toLowerCase()); // Normalize username
        System.out.println("Stored OTP for " + username + ": " + storedOtp); // Debug log
        System.out.println("Entered OTP: " + otp.trim()); // Debug log
        return storedOtp != null && storedOtp.equals(otp.trim());
    }

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";
        String hashedPassword = encoder.encode(rawPassword);

        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);
    }

}
