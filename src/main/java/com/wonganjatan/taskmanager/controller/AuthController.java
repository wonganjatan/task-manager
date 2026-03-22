package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.*;
import com.wonganjatan.taskmanager.service.JwtService;
import com.wonganjatan.taskmanager.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final JwtService  jwtService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form) {

        try {
            Optional<User> user = userService.login(form);
            String token = jwtService.generateToken(user.get());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AuthResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(true, e.getMessage()));
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
                    .body(new AuthResponse(false, "Registered"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(true, e.getMessage()));
        }
    }
}
