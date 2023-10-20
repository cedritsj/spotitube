package resources;

import com.example.spotitube.spotitubeapp.resources.TrackResource;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import com.example.spotitube.spotitubeapp.services.LoginService;
import com.example.spotitube.spotitubeapp.services.TrackService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackResourceTest {

    private final String token = "1000-1000-1000";
    private TrackResource sut;

    private TrackService trackService;
    private LoginService loginService;

    private TrackDTO trackDTO;
    private TrackResponseDTO trackResponseDTO;

    @BeforeEach
    public void Setup() {
        this.sut = new TrackResource();

        trackService = mock(TrackService.class);
        loginService = mock(LoginService.class);

        this.sut.setTrackService(trackService);
        this.sut.setLoginService(loginService);

        this.trackDTO = new TrackDTO();
        this.trackDTO.setId(1);

        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(trackDTO);

        this.trackResponseDTO = new TrackResponseDTO();
        this.trackResponseDTO.setTracks(tracks);
    }

    @Test
    void testRetrieveAllTracksNotInPlaylistSuccessfully() {
        when(trackService.getTracksNotInPlaylist(1)).thenReturn(trackResponseDTO);

        Response result = sut.getTracksNotInPlaylist(token, 1);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(trackResponseDTO, result.getEntity());
    }
}
