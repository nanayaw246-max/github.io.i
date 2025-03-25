package com.example.semproject;

import com.example.semproject.Model.Sign;
import com.example.semproject.Repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.semproject.UserService.UserServices; // Ensure UserService exists in this package or update the package path

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepo userRepo; // Mock the UserRepo dependency

    @Mock
    private PasswordEncoder passwordEncoder; // Mock the PasswordEncoder dependency

    @InjectMocks
    private UserServices userServices; // Inject mocks into the UserServices class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    void testAddUser_Success() {
        // Arrange
        Sign sign = new Sign("John Doe", "john.doe@example.com", "Password@123", "john_doe");
        when(userRepo.existsByUsername("john_doe")).thenReturn(false); // Mock username check
        when(userRepo.existsByEmail("john.doe@example.com")).thenReturn(false); // Mock email check
        when(passwordEncoder.encode("Password@123")).thenReturn("hashedPassword"); // Mock password hashing

        // Act
        userServices.addUser(sign);

        // Assert
        assertEquals("hashedPassword", sign.getPassword()); // Verify password is hashed
        verify(userRepo, times(1)).save(sign); // Verify save method is called once
    }

    @Test
    void testAddUser_UsernameExists() {
        // Arrange
        Sign sign = new Sign("John Doe", "john.doe@example.com", "Password@123", "john_doe");
        when(userRepo.existsByUsername("john_doe")).thenReturn(true); // Mock username exists

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userServices.addUser(sign));
        assertEquals("Username already exists.", exception.getMessage());
        verify(userRepo, never()).save(any(Sign.class)); // Verify save is never called
    }

    @Test
    void testAddUser_EmailExists() {
        // Arrange
        Sign sign = new Sign("John Doe", "john.doe@example.com", "Password@123", "john_doe");
        when(userRepo.existsByUsername("john_doe")).thenReturn(false); // Mock username does not exist
        when(userRepo.existsByEmail("john.doe@example.com")).thenReturn(true); // Mock email exists

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userServices.addUser(sign));
        assertEquals("Email already exists.", exception.getMessage());
        verify(userRepo, never()).save(any(Sign.class)); // Verify save is never called
    }
}
