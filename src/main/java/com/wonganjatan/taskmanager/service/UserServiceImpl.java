package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.User;
import com.wonganjatan.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<User> login(String username, String password) {
        return repo.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    @Override
    public void save(User user) {
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already exist");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException("Password must include both letters and numbers and contain " +
                    "at least one uppercase letter and one special character e.g., !, @, #, $, etc.)");
        }

        repo.save(user);
    }

    // Helper to match password pattern
    private boolean isValidPassword(String plainPassword) {
        String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).+$";

        return plainPassword.matches(pattern);
    }
}
