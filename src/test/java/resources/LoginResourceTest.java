package resources;

import com.example.spotitube.spotitubeapp.resources.LoginResource;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.LoginResponseDTO;
import com.example.spotitube.spotitubeapp.services.LoginService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginResourceTest {
    private LoginResource sut;
    private LoginService loginService;
    private LoginRequestDTO loginRequestDTO;

    private final static String user = "user";
    private final static String password = "password";


    @BeforeEach
    public void setup() {
        this.sut = new LoginResource();

        this.loginService = mock(LoginService.class);

        this.sut.setLoginService(loginService);

        this.loginRequestDTO = new LoginRequestDTO(user, password);
    }

    @Test
    void loginSuccessfully() {
        when(loginService.authenticateUser(loginRequestDTO)).thenReturn(new LoginResponseDTO(user, "1000"));

        var result = sut.login(loginRequestDTO);

        assertEquals(Response.status(Response.Status.OK), result.getStatus());
    }
}
