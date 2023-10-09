package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.exceptions.AuthenticationException;
import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.exceptions.InvalidCredentialsException;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.LoginResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@Default
@ApplicationScoped
public class LoginService {
    private LoginDAO loginDAO;
    private ConnectionManager connectionManager;

    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
        try (Connection conn = connectionManager.startConn()) {
            if (loginDAO.existingUser(conn, loginRequestDTO)) {
                String token = UUID.randomUUID().toString();
                loginDAO.updateUserToken(loginRequestDTO, token);
                return new LoginResponseDTO(loginRequestDTO.getUser(), token);
            } else {
                throw new InvalidCredentialsException();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void verifyToken(String token) throws AuthenticationException {
        loginDAO.verifyToken(token);
    }

    public int getUserID(String token) {
        return loginDAO.getUserID(token);
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}