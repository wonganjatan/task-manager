package com.wonganjatan.taskmanager.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;


}
