package resources;

import com.example.spotitube.spotitubeapp.resources.TrackResource;
import com.example.spotitube.spotitubeapp.services.LoginService;
import com.example.spotitube.spotitubeapp.services.TrackService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TrackResourceTest {

    private TrackResource sut;

    @BeforeEach
    public void Setup() {
        this.sut = new TrackResource();

        TrackService trackService = mock(TrackService.class);
        LoginService loginService = mock(LoginService.class);

        this.sut.setTrackService(trackService);
        this.sut.setLoginService(loginService);
    }

    @Test
    void testRetrieveAllTracksNotInPlaylistSuccessfully() {
        assertEquals(Response.Status.OK.getStatusCode(), sut.getTracksNotInPlaylist("token", 1).getStatus());
    }
}
