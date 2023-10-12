package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.exceptions.AuthenticationException;
import com.example.spotitube.spotitubeapp.exceptions.InvalidCredentialsException;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.LoginResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.util.UUID;

@Default
@ApplicationScoped
public class LoginService {
    private LoginDAO loginDAO;

    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
        if (loginDAO.existingUser(loginRequestDTO)) {
            String token = UUID.randomUUID().toString();
            loginDAO.updateUserToken(loginRequestDTO, token);
            return new LoginResponseDTO(loginRequestDTO.getUser(), token);
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public void verifyToken(String token) {
        loginDAO.verifyToken(token);
    }

    public int getUserID(String token) {
        return loginDAO.getUserID(token);
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }
}