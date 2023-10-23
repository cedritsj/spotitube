package services;

import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import com.example.spotitube.spotitubeapp.services.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

public class TrackServiceTest {

    ArrayList<PlaylistDTO> playlists = new ArrayList<>();
    ArrayList<TrackDTO> tracks = new ArrayList<>();
    private TrackService sut;

    private TrackDAO trackDAO;

    private TrackDTO trackDTO;

    private PlaylistDTO playlistDTO;
    private TrackResponseDTO trackResponseDTO;

    @BeforeEach
    public void setup() {
        sut = new TrackService();

        this.trackDAO = mock(TrackDAO.class);
        this.trackResponseDTO = new TrackResponseDTO();
        this.trackDTO = new TrackDTO();
        this.playlistDTO = new PlaylistDTO();
        this.sut.setTrackDAO(trackDAO);

        trackDTO.setId(1);
        trackDTO.setTitle("track");
        trackDTO.setPerformer("performer");
        trackDTO.setDuration(100);
        trackDTO.setAlbum("album");
        trackDTO.setPlaycount(1);
        trackDTO.setPublicationDate("2020-01-01");
        tracks.add(trackDTO);

        trackResponseDTO.setTracks(tracks);
    }

    @Test
    void getAllTracksNotInPlaylistSuccessfullyWithRightResponse() {
        when(trackDAO.getTracksNotInPlaylist(anyInt())).thenReturn(tracks);

        TrackResponseDTO result = sut.getTracksNotInPlaylist(1);

        assertInstanceOf(TrackResponseDTO.class, result);
        assertEquals(trackResponseDTO.getTracks(), result.getTracks());
    }
}
