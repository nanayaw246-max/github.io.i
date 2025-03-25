package com.example.semproject;

import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

//import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.mockito.Mockito.*;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testSendEmail() {
        assertDoesNotThrow(() -> {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("recipient@example.com"); // Replace with a valid recipient email
            helper.setSubject("Test Email");
            helper.setText("This is a test email sent from the Spring Boot application.");

            mailSender.send(message);
            System.out.println("Email sent successfully!");
        });
    }
}

