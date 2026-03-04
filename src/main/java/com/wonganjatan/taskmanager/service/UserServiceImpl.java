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
}
