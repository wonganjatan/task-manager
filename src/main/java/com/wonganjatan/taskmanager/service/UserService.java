package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> login(String username, String password);
}
