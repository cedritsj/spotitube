package services;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import com.example.spotitube.spotitubeapp.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {

    ArrayList<PlaylistDTO> playlists = new ArrayList<>();
    ArrayList<TrackDTO> tracks = new ArrayList<>();
    private final int userId = 1;
    private PlaylistService sut;
    private PlaylistDTO playlistDTO;
    private TrackDTO trackDTO;
    private PlaylistResponseDTO playlistResponseDTO;
    private TrackResponseDTO trackResponseDTO;
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;

    @BeforeEach
    public void Setup() {
        this.sut = new PlaylistService();

        this.playlistDAO = mock(PlaylistDAO.class);
        this.playlistDTO = mock(PlaylistDTO.class);
        this.playlistResponseDTO = new PlaylistResponseDTO(playlists, 0);
        this.trackResponseDTO = mock(TrackResponseDTO.class);
        this.trackDAO = mock(TrackDAO.class);
        this.trackDTO = mock(TrackDTO.class);


        this.sut.setPlaylistDAO(playlistDAO);
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

        playlistDTO.setId(1);
        playlistDTO.setName("playlist");
        playlistDTO.setOwner(true);
        playlistDTO.setOwnerID(1);
        playlistDTO.setTracks(tracks);
        playlists.add(playlistDTO);

        playlistResponseDTO.setPlaylists(playlists);
        playlistResponseDTO.setLength(playlists.stream().mapToInt(PlaylistDTO::getLength).sum());
    }

    @Test
    void testGetAllPlaylistsSuccessfullyWithRightResponse() {
        when(playlistDAO.getAll()).thenReturn(playlists);

        PlaylistResponseDTO result = sut.getAllPlaylists(userId);

        assertInstanceOf(PlaylistResponseDTO.class, result);
        assertEquals(playlistResponseDTO.getLength(), result.getLength());
        assertEquals(playlistResponseDTO.getPlaylists(), result.getPlaylists());
    }

    @Test
    void testAddPlaylistSuccessfully() {
        sut.addPlaylist(playlistDTO, userId);

        verify(playlistDAO, times(1)).insert(playlistDTO);
    }

    @Test
    void testEditPlaylistSuccessfully() {
        sut.editPlaylist(playlistDTO, playlistDTO.getId());

        verify(playlistDAO, times(1)).update(playlistDTO, playlistDTO.getId());
    }

    @Test
    void testDeletePlaylistSuccessfully() {
        sut.deletePlaylist(playlistDTO.getId());

        verify(playlistDAO, times(1)).delete(playlistDTO.getId());
    }

    @Test
    void testGetAllTracksPerPlaylistSuccessfullyWithRightResponse() {
        when(trackDAO.getTracksFromPlaylist(playlistDTO.getId())).thenReturn(tracks);

        TrackResponseDTO result = sut.getTracksPerPlaylist(playlistDTO.getId());

        assertInstanceOf(TrackResponseDTO.class, result);
        assertEquals(tracks, result.getTracks());
    }

    @Test
    void testAddTrackToPlaylistSuccessfully() {
        sut.addTrackToPlaylist(playlistDTO.getId(), trackDTO);

        verify(trackDAO, times(1)).update(trackDTO, trackDTO.getId());
        verify(trackDAO, times(1)).insertTrackInPlaylist(playlistDTO.getId(), trackDTO);
    }

    @Test
    void testDeleteTrackFromPlaylistSuccessfully() {
        sut.removeTrackFromPlaylist(playlistDTO.getId(), trackDTO.getId());

        verify(trackDAO, times(1)).deleteTracksFromPlaylist(playlistDTO.getId(), trackDTO.getId());
    }
}
