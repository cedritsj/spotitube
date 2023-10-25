package com.example.spotitube.spotitubeapp.datasource.dbconnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConnectionManagerTest {

    private ConnectionManager sut;

    private DataProperties mockedDataProperties;

    @BeforeEach
    public void setup() {
        sut = new ConnectionManager();
        mockedDataProperties = mock(DataProperties.class);
        sut.setDataProperties(mockedDataProperties);

        when(mockedDataProperties.getConnectionString()).thenReturn("jdbc:mysql://localhost:1433/spotitube?serverTimezone=UTC&user=spotitube&password=spotitube&useSSL=false");
    }

    @Test
    public void getConnectionReturnsConnection() throws SQLException {
        assertNotNull(sut.getConnection());
    }

}