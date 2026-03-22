package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.form.LoginForm;
import com.wonganjatan.taskmanager.model.entity.User;
import com.wonganjatan.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> login(LoginForm form) {
        Optional<User> user = Optional.of(userRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username does not exist")));

        if (!passwordEncoder.matches(form.getPassword(), user.get().getPassword())) {
            throw new IllegalArgumentException("Password is incorrect");
        }

        return user;
    }


    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void save(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username is already exist");
        }

        userRepository.save(user);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
