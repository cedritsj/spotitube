package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.resources.dto.UserDTO;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class LoginDAO extends BaseDAO<UserDTO> {

    public UserDTO getUserWithLoginRequest(LoginRequestDTO loginRequestDTO) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = getUserWithLoginRequestStatement(connection, loginRequestDTO);
            UserDTO result = buildFromResultSet(statement.executeQuery()).get(0);
            connection.close();
            return result;
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public Optional<UserDTO> getUserWithToken(String token) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = getUserWithTokenStatement(connection, token);
            Optional<UserDTO> result = Optional.of(buildFromResultSet(statement.executeQuery()).get(0));
            connection.close();
            return result;
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    @Override
    public PreparedStatement statementBuilder(Connection connection, String action, Optional<UserDTO> userDTO, Optional<Integer> id) {
        try {
            if (action.equals("UPDATE") && userDTO.isPresent()) {
                PreparedStatement statement = connection.prepareStatement("UPDATE users SET token = ? WHERE user = ?;");
                statement.setString(1, userDTO.get().getToken());
                statement.setString(2, userDTO.get().getUser());
                return statement;
            } else {
                return null;
            }
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public PreparedStatement getUserWithLoginRequestStatement(Connection connection, LoginRequestDTO loginRequestDTO) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user = ? AND password = ?;");
        statement.setString(1, loginRequestDTO.getUser());
        statement.setString(2, DigestUtils.sha256Hex(loginRequestDTO.getPassword()));
        return statement;
    }

    public PreparedStatement getUserWithTokenStatement(Connection connection, String token) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE token = ?;");
        statement.setString(1, token);
        return statement;
    }

    @Override
    public ArrayList<UserDTO> buildFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<UserDTO> users = new ArrayList<>();
        while (rs.next()) {
            UserDTO user = new UserDTO(
                    rs.getInt("id"),
                    rs.getString("token"),
                    rs.getString("user")

            );
            users.add(user);
        }
        rs.close();
        return users;
    }
}