package com.wonganjatan.taskmanager.model;

import jakarta.validation.constraints.NotBlank;

public class LoginForm {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    // Setter
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    // Getter
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
