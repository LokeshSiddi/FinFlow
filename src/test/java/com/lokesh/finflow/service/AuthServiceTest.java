package com.lokesh.finflow.service;

import com.lokesh.finflow.dto.request.RegisterRequest;
import com.lokesh.finflow.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_ShouldThrowExceptionIfEmailAlreadyExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest("John Doe", "test@zorvyn.io", "password123");

        // Tell the mock repository to pretend the email is already in the database
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request)
        );

        assertEquals("Email already in use", exception.getMessage());
    }
}