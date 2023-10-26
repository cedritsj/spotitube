package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.resources.LoginResource;
import com.example.spotitube.spotitubeapp.resources.dto.UserDTO;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.resources.interfaces.ILoginService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginResourceTest {
    private LoginResource sut;
    private ILoginService ILoginService;
    private LoginRequestDTO loginRequestDTO;
    private UserDTO userDTO;

    private final static String user = "user";
    private final static String password = "password";


    @BeforeEach
    public void setup() {
        this.sut = new LoginResource();

        this.ILoginService = mock(ILoginService.class);

        this.sut.setLoginService(ILoginService);

        this.loginRequestDTO = new LoginRequestDTO(user, password);

        this.userDTO = new UserDTO(1, user, "1000-1000-1000");
    }

    @Test
    void testLoginSuccessfullyReturnResponse200() {
        when(ILoginService.login(loginRequestDTO)).thenReturn(userDTO);

        Response result = sut.login(loginRequestDTO);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(userDTO, result.getEntity());
    }
}
