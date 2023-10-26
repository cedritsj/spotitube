package com.example.spotitube.spotitubeapp.datasource.dbconnection;

import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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