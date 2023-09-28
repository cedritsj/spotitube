package com.example.spotitube.spotitubeapp.datasource.dbconnection;

import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {
    private Connection connection;
    @Inject
    private DataProperties properties;

    private Logger logger = Logger.getLogger(getClass().getName());


    public Connection getConnection() {
        return connection;
    }

    public Connection startConn() {
        try {
            Class.forName(properties.driverString());
            connection = DriverManager.getConnection(properties.connectionString());
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return connection;
    }

    public void closeConn() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}