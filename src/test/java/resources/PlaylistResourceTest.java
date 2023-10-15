package resources;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.resources.PlaylistResource;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.services.LoginService;
import com.example.spotitube.spotitubeapp.services.PlaylistService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlaylistResourceTest {

    private final String token = "token";
    private PlaylistResource sut;
    private PlaylistService playlistService;
    private LoginService loginService;
    private PlaylistDTO playlistDTO;

    private PlaylistDAO playlistDAO;

    @BeforeEach
    public void Setup() {
        this.sut = new PlaylistResource();

        this.playlistService = mock(PlaylistService.class);
        this.loginService = mock(LoginService.class);

        this.playlistDTO = mock(PlaylistDTO.class);

        this.sut.setPlaylistService(playlistService);
        this.sut.setLoginService(loginService);

        this.playlistDAO = mock(PlaylistDAO.class);
    }

    @Test
    void testRetrieveAllPlaylistsSuccessfully() {
        doNothing().when(loginService).verifyToken(token);
        assertEquals(Response.Status.OK.getStatusCode(), sut.getPlaylists(token).getStatus());
    }

    @Test
    void testAddPlaylistSuccessfully() {
        assertEquals(Response.Status.CREATED.getStatusCode(), sut.addPlaylist(playlistDTO, token).getStatus());
    }
}
