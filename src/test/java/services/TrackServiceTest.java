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

    private ConnectionManager connectionManager;

    @BeforeEach
    public void setup() {
        sut = new TrackService();

        this.trackDAO = mock(TrackDAO.class);
        this.connectionManager = mock(ConnectionManager.class);
        this.trackResponseDTO = mock(TrackResponseDTO.class);
        this.trackDTO = new TrackDTO();
        this.playlistDTO = new PlaylistDTO();

        this.sut.setTrackDAO(trackDAO);
        this.sut.setConnectionManager(connectionManager);

        trackDTO.setId(1);
        trackDTO.setTitle("track");
        trackDTO.setPerformer("performer");
        trackDTO.setDuration(100);
        trackDTO.setAlbum("album");
        trackDTO.setPlaycount(1);
        trackDTO.setPublicationDate("2020-01-01");
        tracks.add(trackDTO);

        trackResponseDTO.setTracks(tracks);

        playlistDTO.setId(1);
        playlistDTO.setName("playlist");
        playlistDTO.setOwner(true);
        playlistDTO.setOwnerID(1);
        playlistDTO.setTracks(tracks);
        playlists.add(playlistDTO);

    }

    @Test
    void getAllTracksNotInPlaylistSuccessfullyWithRightResponse() {
        when(trackDAO.getTracksNotInPlaylist(any(Connection.class), anyInt())).thenReturn(tracks);

        TrackResponseDTO result = sut.getTracksNotInPlaylist(1);

        assertEquals(trackResponseDTO.getTracks(), result.getTracks());
        assertInstanceOf(TrackResponseDTO.class, result);
    }
}
