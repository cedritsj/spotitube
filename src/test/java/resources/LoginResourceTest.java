package resources;

import com.example.spotitube.spotitubeapp.exceptions.InvalidCredentialsException;
import com.example.spotitube.spotitubeapp.resources.LoginResource;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.LoginResponseDTO;
import com.example.spotitube.spotitubeapp.services.LoginService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginResourceTest {
    private LoginResource sut;
    private LoginService loginService;
    private LoginRequestDTO loginRequestDTO;
    private LoginResponseDTO loginResponseDTO;

    private final static String user = "user";
    private final static String password = "password";


    @BeforeEach
    public void setup() {
        this.sut = new LoginResource();

        this.loginService = mock(LoginService.class);

        this.sut.setLoginService(loginService);

        this.loginRequestDTO = new LoginRequestDTO(user, password);

        this.loginResponseDTO = new LoginResponseDTO(user, "1000-1000-1000");
    }

    @Test
    void testLoginSuccessfullyReturnResponse200() {
        when(loginService.authenticateUser(loginRequestDTO)).thenReturn(loginResponseDTO);

        Response result = sut.login(loginRequestDTO);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(loginResponseDTO, result.getEntity());
    }
}
