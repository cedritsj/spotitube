package com.example.spotitube.spotitubeapp.datasource.dbconnection;

import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    private Connection connection;
    private DataProperties properties;

    public Connection startConn() {
        try {
            Class.forName(properties.driverString());
            connection = DriverManager.getConnection(properties.connectionString());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return connection;
    }

    public void closeConn() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Inject
    private void setDataProperties(DataProperties properties) {
        this.properties = properties;
    }

}