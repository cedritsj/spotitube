package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.exceptions.AuthenticationException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LoginDAO {
    @Inject
    private ConnectionManager connectionManager;
    private Logger logger = Logger.getLogger(getClass().getName());

    public boolean existingUser(Connection conn, LoginRequestDTO loginRequestDTO) throws SQLException {
        connectionManager.startConn();
        PreparedStatement statement = getUserWithStatement(conn, loginRequestDTO);
        boolean userExists = hasSingleResult(statement.executeQuery());
        connectionManager.closeConn();
        return userExists;
    }

    public void updateUserToken(LoginRequestDTO loginRequestDTO, String token) throws SQLException {
        String sql = "UPDATE spotitube.users SET token = ? WHERE user = ?;";
        try (Connection conn = connectionManager.startConn();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, loginRequestDTO.getUser());
            preparedStatement.executeUpdate();
        }
    }

    public PreparedStatement getUserWithStatement(Connection connection, LoginRequestDTO loginRequestDTO) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM spotitube.users WHERE user = ? AND password = ?");
        statement.setString(1, loginRequestDTO.getUser());
        statement.setString(2, loginRequestDTO.getPassword());
        return statement;
    }

    public void verifyToken(String token) throws AuthenticationException {
        String sql = "SELECT token FROM spotitube.users WHERE token = ?";
        try (Connection conn = connectionManager.startConn();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, token);
            ResultSet result = statement.executeQuery();

            if (!result.isBeforeFirst()) {
                throw new AuthenticationException();
            }
        } catch (SQLException e) {
            throw new AuthenticationException();
        }
    }

    public boolean hasSingleResult(ResultSet rs) throws SQLException {
        return rs.next() && !rs.next();
    }
}