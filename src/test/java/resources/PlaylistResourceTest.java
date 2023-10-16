package resources;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.resources.PlaylistResource;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
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

    private final int userId = 1;
    private final String token = "token";
    private PlaylistResource sut;
    private PlaylistService playlistService;
    private LoginService loginService;
    private PlaylistDTO playlistDTO;
    private TrackDTO trackDTO;
    private TrackResponseDTO trackResponseDTO;

    private PlaylistResponseDTO playlistResponseDTO;

    @BeforeEach
    public void Setup() {
        this.sut = new PlaylistResource();

        this.playlistService = mock(PlaylistService.class);
        this.loginService = mock(LoginService.class);

        this.playlistDTO = mock(PlaylistDTO.class);
        this.trackDTO = mock(TrackDTO.class);

        this.playlistResponseDTO = new PlaylistResponseDTO();
        this.trackResponseDTO = new TrackResponseDTO();

        this.sut.setPlaylistService(playlistService);
        this.sut.setLoginService(loginService);

        trackDTO.setId(1);
        trackResponseDTO.getTracks().add(trackDTO);
    }

    @Test
    void testRetrieveAllPlaylistsSuccessfullyWithRightResponse() {
        doNothing().when(loginService).verifyToken(token);
        assertEquals(Response.Status.OK.getStatusCode(), sut.getPlaylists(token).getStatus());
        assertEquals(playlistResponseDTO, sut.getPlaylists(token).getEntity());
    }

    @Test
    void testAddPlaylistSuccessfullyWithRightResponse() {
        doNothing().when(loginService).verifyToken(token);
        assertEquals(Response.Status.CREATED.getStatusCode(), sut.addPlaylist(playlistDTO, token).getStatus());
        assertEquals(playlistResponseDTO, sut.addPlaylist(playlistDTO, token).getEntity());
    }

    @Test
    void testDeletePlaylistSuccessfullyWithRightResponse() {
        doNothing().when(loginService).verifyToken(token);
        assertEquals(Response.Status.OK.getStatusCode(), sut.deletePlaylist(playlistDTO.getId(), token).getStatus());
        assertEquals(playlistResponseDTO, sut.deletePlaylist(playlistDTO.getId(), token).getEntity());
    }

    @Test
    void testEditPlaylistSuccessfullyWithRightResponse() {
        doNothing().when(loginService).verifyToken(token);

//        when(playlistService.editPlaylist(playlistDTO, 1)).thenReturn(playlistResponseDTO);

        assertEquals(Response.Status.OK.getStatusCode(), sut.editPlaylist(playlistDTO, playlistDTO.getId(), token).getStatus());
        assertEquals(playlistResponseDTO, sut.editPlaylist(playlistDTO, playlistDTO.getId(), token).getEntity());
    }

    @Test
    void testGetAllTracksFromPlaylistSuccessfullyWithRightResponse() {
        doNothing().when(loginService).verifyToken(token);

        when(playlistService.getTracksPerPlaylist(playlistDTO.getId())).thenReturn(trackResponseDTO);

        assertEquals(Response.Status.OK.getStatusCode(), sut.getTracksFromPlaylist(playlistDTO.getId(), token).getStatus());
        assertEquals(trackResponseDTO, sut.getTracksFromPlaylist(playlistDTO.getId(), token).getEntity());
    }

    @Test
    void testAddTrackToPlaylistSuccessfullyWithRightResponse() {
        doNothing().when(loginService).verifyToken(token);

        when(playlistService.addTrackToPlaylist(playlistDTO.getId(), trackDTO)).thenReturn(trackResponseDTO);

        assertEquals(Response.Status.CREATED.getStatusCode(), sut.addTrackToPlaylist(playlistDTO.getId(), trackDTO, token).getStatus());
        assertEquals(trackResponseDTO, sut.addTrackToPlaylist(playlistDTO.getId(), trackDTO, token).getEntity());
    }

    @Test
    void testRemoveTrackFromPlaylistSuccesfullyWithRightResponse() {
        doNothing().when(loginService).verifyToken(token);

        when(playlistService.removeTrackFromPlaylist(playlistDTO.getId(), 1)).thenReturn(trackResponseDTO);

        assertEquals(Response.Status.OK.getStatusCode(), sut.removeTrackFromPlaylist(playlistDTO.getId(), 1, token).getStatus());
        assertEquals(trackResponseDTO, sut.removeTrackFromPlaylist(playlistDTO.getId(), 1, token).getEntity());
    }
}
