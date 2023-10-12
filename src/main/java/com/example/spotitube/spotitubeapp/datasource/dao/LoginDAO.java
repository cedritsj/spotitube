package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.exceptions.AuthenticationException;
import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private ConnectionManager connectionManager;

    public boolean existingUser(LoginRequestDTO loginRequestDTO) {
        try {
            PreparedStatement statement = getUserWithStatement(getConnection(), loginRequestDTO);
            boolean userExists = hasSingleResult(statement.executeQuery());
            connectionManager.closeConn();
            return userExists;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }


    }

    public void updateUserToken(LoginRequestDTO loginRequestDTO, String token) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE users SET token = ? WHERE user = ?;");
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, loginRequestDTO.getUser());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public PreparedStatement getUserWithStatement(Connection connection, LoginRequestDTO loginRequestDTO) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user = ? AND password = ?");
            statement.setString(1, loginRequestDTO.getUser());
            statement.setString(2, loginRequestDTO.getPassword());
            return statement;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public void verifyToken(String token) throws AuthenticationException {
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT token FROM users WHERE token = ?");
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

    public int getUserID(String token) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT id FROM users WHERE token = ?");
            statement.setString(1, token);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("id");
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Connection getConnection() {
        return connectionManager.startConn();
    }

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}