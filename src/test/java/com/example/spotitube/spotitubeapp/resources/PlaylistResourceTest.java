package com.example.spotitube.spotitubeapp.resources;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.resources.PlaylistResource;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import com.example.spotitube.spotitubeapp.resources.interfaces.ILoginService;
import com.example.spotitube.spotitubeapp.resources.interfaces.IPlaylistService;
import com.example.spotitube.spotitubeapp.services.TrackService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlaylistResourceTest {
    private final int userId = 1;
    private final String token = "token";
    private PlaylistResource sut;
    private IPlaylistService IPlaylistService;
    private ILoginService ILoginService;
    private PlaylistDTO playlistDTO;
    private TrackDTO trackDTO;
    private TrackResponseDTO trackResponseDTO;

    private PlaylistResponseDTO playlistResponseDTO;

    @BeforeEach
    public void Setup() {
        this.sut = new PlaylistResource();

        this.IPlaylistService = mock(IPlaylistService.class);
        this.ILoginService = mock(ILoginService.class);

        this.playlistDTO = mock(PlaylistDTO.class);
        this.trackDTO = mock(TrackDTO.class);

        this.playlistResponseDTO = new PlaylistResponseDTO();
        this.trackResponseDTO = new TrackResponseDTO();

        this.sut.setPlaylistService(IPlaylistService);
        this.sut.setLoginService(ILoginService);

        trackDTO.setId(1);
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        tracks.add(trackDTO);
        trackResponseDTO.setTracks(tracks);

        playlistDTO.setId(1);
        playlistDTO.setTracks(tracks);
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlistDTO);
        playlistResponseDTO.setPlaylists(playlists);
    }

    @Test
    void testRetrieveAllPlaylistsSuccessfullyWithRightResponse() {
        when(IPlaylistService.getAllPlaylists(userId)).thenReturn(playlistResponseDTO);
        when(ILoginService.getUserID(token)).thenReturn(userId);

        Response result = sut.getPlaylists(token);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(playlistResponseDTO, result.getEntity());
    }

    @Test
    void testAddPlaylistSuccessfullyWithRightResponse() {
        doNothing().when(IPlaylistService).addPlaylist(playlistDTO, userId);

        when(ILoginService.getUserID(token)).thenReturn(userId);
        when(IPlaylistService.getAllPlaylists(userId)).thenReturn(playlistResponseDTO);

        Response result = sut.addPlaylist(playlistDTO, token);

        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertEquals(playlistResponseDTO, result.getEntity());
    }

    @Test
    void testDeletePlaylistSuccessfullyWithRightResponse() {
        doNothing().when(IPlaylistService).deletePlaylist(1);
        when(IPlaylistService.getAllPlaylists(userId)).thenReturn(playlistResponseDTO);
        when(ILoginService.getUserID(token)).thenReturn(userId);

        Response result = sut.deletePlaylist(playlistDTO.getId(), token);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(playlistResponseDTO, result.getEntity());
    }

    @Test
    void testEditPlaylistSuccessfullyWithRightResponse() {
        doNothing().when(IPlaylistService).editPlaylist(playlistDTO, 1);
        when(IPlaylistService.getAllPlaylists(userId)).thenReturn(playlistResponseDTO);
        when(ILoginService.getUserID(token)).thenReturn(userId);

        Response result = sut.editPlaylist(playlistDTO, playlistDTO.getId(), token);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(playlistResponseDTO, result.getEntity());
    }

    @Test
    void testGetAllTracksFromPlaylistSuccessfullyWithRightResponse() {
        when(IPlaylistService.getTracksPerPlaylist(playlistDTO.getId())).thenReturn(trackResponseDTO);

        Response result = sut.getTracksFromPlaylist(playlistDTO.getId(), token);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(trackResponseDTO, result.getEntity());
    }

    @Test
    void testAddTrackToPlaylistSuccessfullyWithRightResponse() {
        doNothing().when(IPlaylistService).addTrackToPlaylist(trackDTO,1);

        when(IPlaylistService.getTracksPerPlaylist(1)).thenReturn(trackResponseDTO);

        Response result = sut.addTrackToPlaylist(1, trackDTO, token);

        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertEquals(trackResponseDTO, result.getEntity());
    }

    @Test
    void testRemoveTrackFromPlaylistSuccesfullyWithRightResponse() {
        doNothing().when(IPlaylistService).removeTrackFromPlaylist(1, 1);

        when(IPlaylistService.getTracksPerPlaylist(1)).thenReturn(trackResponseDTO);

        Response result = sut.removeTrackFromPlaylist(1, 1, token);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(trackResponseDTO, result.getEntity());
    }
}
