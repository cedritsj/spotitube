package com.example.spotitube.spotitubeapp.exceptions.mapper;

import com.example.spotitube.spotitubeapp.exceptions.AuthenticationException;
import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.exceptions.InvalidCredentialsException;
import com.example.spotitube.spotitubeapp.exceptions.SpotitubeException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpotitubeExceptionMapperTest {

    SpotitubeExceptionMapper sut;
    SpotitubeException exception;

    @BeforeEach
    public void setUp() {
        sut = new SpotitubeExceptionMapper();
    }

    @Test
    void testIfUnauthenticatedExceptiongives401() {
        // Arrange
        exception = new AuthenticationException();

        // Act
        Response response = sut.toResponse(exception);

        //Assert
        assertEquals(401, response.getStatus());
    }

    @Test
    void testIfInvalidCredentialsGives403() {
        // Arrange
        exception = new InvalidCredentialsException();

        // Act
        Response response = sut.toResponse(exception);

        //Assert
        assertEquals(403, response.getStatus());
    }

    @Test
    void testIfDatabaseExceptiongives500() {
        // Arrange
        exception = new DatabaseException("test");

        // Act
        Response response = sut.toResponse(exception);

        //Assert
        assertEquals(500, response.getStatus());
    }
}