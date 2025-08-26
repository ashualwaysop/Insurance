package com.ciklum.insurance.controller;

import com.ciklum.insurance.model.User;
import com.ciklum.insurance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email,
                                      @RequestParam String password) {
        return ResponseEntity.ok(userService.loginUser(email, password));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<User> getUserByMobile(@PathVariable String mobile) {
        return ResponseEntity.ok(userService.getUserByMobile(mobile));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id,
                                           @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
