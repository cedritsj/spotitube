package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public abstract class BaseDAO<T> {

    private ConnectionManager connectionManager;


    public ArrayList<T> getAll() throws SQLException {
        PreparedStatement statement = statementBuilder(getConnection(), "SELECT", Optional.empty(), Optional.empty());
        return buildFromResultSet(statement.executeQuery());
    }


    public T get(int id) throws SQLException {
        PreparedStatement statement = statementBuilder(getConnection(), "SELECT", Optional.empty(), Optional.of(id));
        return buildFromResultSet(statement.executeQuery()).get(0);
    }

    public void insert(T t) throws SQLException {
        PreparedStatement statement = statementBuilder(getConnection(), "INSERT", Optional.of(t), Optional.empty());
        statement.executeUpdate();
    }

    public void update(T t) throws SQLException {
        PreparedStatement statement = statementBuilder(getConnection(), "UPDATE", Optional.of(t), Optional.empty());
        statement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement statement = statementBuilder(getConnection(), "DELETE", Optional.empty(), Optional.of(id));
        statement.executeUpdate();
    }

    public abstract ArrayList<T> buildFromResultSet(ResultSet rs) throws SQLException;

    public abstract PreparedStatement statementBuilder(Connection connection, String action, Optional<T> t, Optional<Integer> id) throws SQLException;

    private Connection getConnection() {
        return connectionManager.startConn();
    }

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}
