package com.example.spotitube.spotitubeapp.datasource.dbconnection;

import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {
    private DataProperties properties;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getConnectionString());
    }

    @Inject
    public void setDataProperties(DataProperties properties) {
        this.properties = properties;
    }

}