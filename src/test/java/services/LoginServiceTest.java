package services;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.exceptions.InvalidCredentialsException;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.LoginResponseDTO;
import com.example.spotitube.spotitubeapp.services.LoginService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    private final String token = "1000-1000-1000";
    private LoginService sut;

    private LoginRequestDTO loginRequestDTO;

    private LoginResponseDTO loginResponseDTO;

    private LoginDAO loginDAO;

    @BeforeEach
    public void Setup() {
        this.sut = new LoginService();

        this.loginDAO = mock(LoginDAO.class);

        this.sut.setLoginDAO(loginDAO);

        this.loginRequestDTO = new LoginRequestDTO("user", "password");

        this.loginResponseDTO = new LoginResponseDTO("user", token);
    }

    @Test
    void testAuthenticateUserSuccessfullyWithRightResponse() {
        when(loginDAO.existingUser(loginRequestDTO)).thenReturn(true);

        doNothing().when(loginDAO).updateUserToken(loginRequestDTO, token);

        LoginResponseDTO result = sut.authenticateUser(loginRequestDTO);

        assertInstanceOf(LoginResponseDTO.class, result);
    }

    @Test
    void testAuthenticatieUserInvalidCredentialsWithRightResponse() {
        doThrow(InvalidCredentialsException.class).when(loginDAO).existingUser(loginRequestDTO);

        doNothing().when(loginDAO).updateUserToken(loginRequestDTO, token);

        assertThrows(InvalidCredentialsException.class, () -> sut.authenticateUser(loginRequestDTO));
    }

    @Test
    void testGetUserIdWithTokenSuccessfully() {
        when(loginDAO.getUserID(token)).thenReturn(1);

        int result = sut.getUserID(token);

        assertEquals(1, result);
    }
}


