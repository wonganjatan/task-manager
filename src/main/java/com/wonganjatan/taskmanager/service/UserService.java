package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.User;

import java.util.Optional;

public interface UserService {
    String encodePassword(String password);
    void save(User user);
}
