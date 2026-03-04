package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.User;
import com.wonganjatan.taskmanager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        Optional<User> user = userService.login(username, password);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "dashboard";
        } else {
            model.addAttribute("error", "Invalid credentils");
            return "login";
        }
    }
}
