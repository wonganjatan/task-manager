package com.wonganjatan.taskmanager.controller;

import com.wonganjatan.taskmanager.model.Role;
import com.wonganjatan.taskmanager.model.User;
import com.wonganjatan.taskmanager.model.UserForm;
import com.wonganjatan.taskmanager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            Model model,
            HttpSession session) {

        Optional<User> user = userService.login(username, password);

        if (user.isPresent()) {
            session.setAttribute("loggedInUser", user.get());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("authError", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/registration")
    public String showUserRegistrationForm(Model model) {

        model.addAttribute("userRegistrationForm", new UserForm());

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @Valid @ModelAttribute("userRegistrationForm") UserForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "registration";
        }

        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(userService.encodePassword(form.getPassword()));
        user.setRole(Role.USER);

        try {
            userService.save(user);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("authError", e.getMessage());
            return "registration";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
