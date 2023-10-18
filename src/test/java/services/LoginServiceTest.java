package services;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.exceptions.AuthenticationException;
import com.example.spotitube.spotitubeapp.exceptions.InvalidCredentialsException;
import com.example.spotitube.spotitubeapp.resources.dto.UserDTO;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.services.LoginService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    private final String token = "1000-1000-1000";
    private LoginService sut;

    private LoginRequestDTO loginRequestDTO;

    private UserDTO userDTO;

    private LoginDAO loginDAO;

    @BeforeEach
    public void Setup() {
        this.sut = new LoginService();

        this.loginDAO = mock(LoginDAO.class);

        this.sut.setLoginDAO(loginDAO);

        this.loginRequestDTO = new LoginRequestDTO("user", "password");

        this.userDTO = new UserDTO(1,"user", token);
    }

    @Test
    void testAuthenticateUserSuccessfullyWithRightResponse() {
        when(loginDAO.getUserWithLoginRequest(loginRequestDTO)).thenReturn(userDTO);

        doNothing().when(loginDAO).update(userDTO, userDTO.getId());

        UserDTO result = sut.login(loginRequestDTO);

        assertInstanceOf(UserDTO.class, result);
    }

    @Test
    void testLoginWithThrowsInvalidCredentials() {
        when(loginDAO.getUserWithLoginRequest(loginRequestDTO)).thenReturn(null);

        Exception exception = assertThrows(InvalidCredentialsException.class, () -> sut.login(loginRequestDTO));

        String expectedMessage = "Invalid credentials";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetUserWithTokenSuccessfully() {
        when(loginDAO.getUserWithToken(token)).thenReturn(Optional.of(userDTO));
        assertEquals(userDTO, sut.getUserWithToken(token));
    }
    
    @Test
    void testGetUserWithTokenThrowsAuthenticationException() {
        when(loginDAO.getUserWithToken(token)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AuthenticationException.class, () -> sut.getUserWithToken(token));

        String expectedMessage = "Authentication failed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testVerifyToken() {
        when(sut.getUserWithToken(token)).thenReturn(userDTO);
        assertEquals(userDTO, sut.getUserWithToken(token));
    }

    @Test
    void testGetUserId() {
        when(sut.getUserWithToken(token)).thenReturn(userDTO);
        assertEquals(userDTO.getId(), sut.getUserWithToken(token).getId());
    }
}


