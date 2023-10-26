package com.example.spotitube.spotitubeapp.resources.dto.request;

public class LoginRequestDTO {
    private String user;
    private String password;

    public LoginRequestDTO() {

    }

    public LoginRequestDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}