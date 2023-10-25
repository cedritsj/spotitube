package com.example.spotitube.spotitubeapp.datasource.dbconnection;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DataPropertiesTest {

    @Test
    public void testIfCanGetConnectionString() throws IOException {
        // Arrange
        DataProperties dataProperties = new DataProperties();

        // Act
        String connectionString = dataProperties.getConnectionString();

        // Assert
        assertEquals("jdbc:mysql://localhost:1433/spotitube?serverTimezone=UTC&user=spotitube&password=spotitube&useSSL=false", connectionString);
    }
}