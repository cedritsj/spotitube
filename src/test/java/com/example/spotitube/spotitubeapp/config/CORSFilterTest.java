package com.example.spotitube.spotitubeapp.config;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CORSFilterTest {

    private CORSFilter sut;
    private ContainerRequestContext mockedRequestContext;
    private ContainerResponseContext mockedResponseContext;
    private MultivaluedHashMap<String, Object> headers;


    @BeforeEach
    public void setUp() {
        sut = new CORSFilter();
        headers = new MultivaluedHashMap<>();
        mockedRequestContext = mock(ContainerRequestContext.class);
        mockedResponseContext = mock(ContainerResponseContext.class);
        Mockito.when(mockedResponseContext.getHeaders()).thenReturn(headers);
    }

    @Test
    public void testCorsFilterAllowsCrossOrigin() {
        // Arrange

        // Act
        sut.filter(mockedRequestContext, mockedResponseContext);

        // Assert
        assertEquals("*", headers.get("Access-Control-Allow-Origin").get(0));
        assertEquals("origin, content-type, accept, authorization", headers.get("Access-Control-Allow-Headers").get(0));
        assertEquals("true", headers.get("Access-Control-Allow-Credentials").get(0));
        assertEquals("GET, POST, PUT, DELETE, OPTIONS, HEAD", headers.get("Access-Control-Allow-Methods").get(0));
        assertEquals("1209600", headers.get("Access-Control-Max-Age").get(0));
    }
}