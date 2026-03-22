package com.wonganjatan.taskmanager.service;

import com.wonganjatan.taskmanager.model.entity.User;

public interface JwtService {
    String generateToken(User user);
    String getUsernameFromToken(String token);
    String getRoleFromToken(String token);
}
