package com.ciklum.insurance.service;

import com.ciklum.insurance.model.User;
import com.ciklum.insurance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("1", "John", null, "Doe", "1990-01-01",
                "9876543210", "john@example.com", "pass123",
                "Delhi", "123456789012", "ABCDE1234F");
    }

    @Test
    void testRegisterUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testLoginUserSuccess() {
        when(userRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(user));

        User loggedIn = userService.loginUser("john@example.com", "pass123");

        assertNotNull(loggedIn);
        assertEquals("john@example.com", loggedIn.getEmail());
    }

    @Test
    void testLoginUserFailure() {
        when(userRepository.findByEmail("wrong@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.loginUser("wrong@example.com", "pass123"));
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User found = userService.getUserById("1");

        assertNotNull(found);
        assertEquals("John", found.getFirstName());
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userService.updateUser("1", user);

        assertEquals("John", updated.getFirstName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser("1");
        verify(userRepository, times(1)).deleteById("1");
    }
}
