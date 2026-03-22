package com.wonganjatan.taskmanager.model.response;

public class AuthResponse {

    private String token;
    private String message;
    private String error;

    public AuthResponse(String token) { this.token = token; }
    public AuthResponse(boolean isError, String message) {
        if (isError) {
            this.error = message;
        } else {
            this.message = message;
        }
    }

    // Setter
    public void setToken(String token) { this.token = token; }
    public void setMessage(String message) { this.message = message; }
    public void setError(String error) { this.error = error; }

    // Getter
    public String getToken() { return token; }
    public String getMessage() { return message; }
    public String getError() { return error; }
}
