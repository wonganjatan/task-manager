package com.wonganjatan.taskmanager.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationDTO {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;

    public RegistrationDTO() {}

    public RegistrationDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Setter
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    // Getter
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
