package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.LoginForm;
import com.wonganjatan.taskmanager.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Optional<User> login(LoginForm form);
    String encodePassword(String password);
    void save(User user);
    Collection<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
}
