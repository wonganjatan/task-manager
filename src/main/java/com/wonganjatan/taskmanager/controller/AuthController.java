package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.entity.User;
import com.wonganjatan.taskmanager.model.dto.LoginForm;
import com.wonganjatan.taskmanager.model.dto.UserForm;
import com.wonganjatan.taskmanager.model.entity.enums.Role;
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
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginForm form) {

        Optional<User> user = userService.login(form);
        String token = jwtService.generateToken(user.get());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registration(
            @Valid
            @RequestBody UserForm form) {

        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(userService.encodePassword(form.getPassword()));
        user.setRole(Role.USER);

        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Registration successful"));
    }
}
