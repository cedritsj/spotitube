package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.exceptions.AuthenticationException;
import com.example.spotitube.spotitubeapp.exceptions.InvalidCredentialsException;
import com.example.spotitube.spotitubeapp.resources.interfaces.ILoginService;
import com.example.spotitube.spotitubeapp.resources.dto.UserDTO;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@Default
@ApplicationScoped
public class LoginService implements ILoginService {
    private LoginDAO loginDAO;

    @Override
    public UserDTO login(LoginRequestDTO loginRequestDTO) {
        UserDTO userDTO = loginDAO.getUserWithLoginRequest(loginRequestDTO);
        if (userDTO == null) {
            throw new InvalidCredentialsException();
        }

        String token = UUID.randomUUID().toString();
        userDTO.setToken(token);
        loginDAO.update(userDTO, userDTO.getId());

        return userDTO;
    }

    @Override
    public UserDTO verifyToken(String token) {
        return getUserWithToken(token);
    }

    @Override
    public int getUserID(String token) {
        return getUserWithToken(token).getId();
    }

    @Override
    public UserDTO getUserWithToken(String token) {
        Optional<UserDTO> userDTO = loginDAO.getUserWithToken(token);

        if (userDTO.isEmpty()) {
            throw new AuthenticationException();
        }

        return userDTO.get();
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }
}