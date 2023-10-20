package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public abstract class BaseDAO<T> {

    private ConnectionManager connectionManager;

    public ArrayList<T> getAll() {
        try {
            PreparedStatement statement = statementBuilder(getConnection(), "SELECT", Optional.empty(), Optional.empty());
            ArrayList<T> result = buildFromResultSet(statement.executeQuery());
            closeConnection();
            return result;
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }


    public T get(int id) {
        try {
            PreparedStatement statement = statementBuilder(getConnection(), "SELECT", Optional.empty(), Optional.of(id));
            T result = buildFromResultSet(statement.executeQuery()).get(0);
            closeConnection();
            return result;
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public void insert(T t) {
        try {
            PreparedStatement statement = statementBuilder(getConnection(), "INSERT", Optional.of(t), Optional.empty());
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public void update(T t, int id) {
        try {
            PreparedStatement statement = statementBuilder(getConnection(), "UPDATE", Optional.of(t), Optional.of(id));
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public void delete(int id) {
        try {
            PreparedStatement statement = statementBuilder(getConnection(), "DELETE", Optional.empty(), Optional.of(id));
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public abstract ArrayList<T> buildFromResultSet(ResultSet rs) throws SQLException;

    public abstract PreparedStatement statementBuilder(Connection connection, String action, Optional<T> t, Optional<Integer> id) throws SQLException;

    public Connection getConnection() {
        return connectionManager.startConn();
    }

    public void closeConnection() {
        connectionManager.closeConn();
    }

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}
