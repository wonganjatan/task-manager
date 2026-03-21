package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.LoginForm;
import com.wonganjatan.taskmanager.model.Role;
import com.wonganjatan.taskmanager.model.User;
import com.wonganjatan.taskmanager.model.UserForm;
import com.wonganjatan.taskmanager.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form) {

        try {
            Optional<User> user = userService.login(form);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "Successfully logged in"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(
            @Valid
            @RequestBody UserForm form) {

        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(userService.encodePassword(form.getPassword()));
        user.setRole(Role.USER);

        try {
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Registered"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
