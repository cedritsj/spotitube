package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistService {

    private TrackService trackService;
    private PlaylistDAO playlistDAO;

    public PlaylistResponseDTO getAllPlaylists(int userID) {
        ArrayList<PlaylistDTO> playlists = playlistDAO.getAll();
        int totalLength = 0;
        for (PlaylistDTO playlist : playlists) {
            playlist.setOwner(userID == playlist.getOwnerID());
            totalLength += playlist.getLength();
        }
        return new PlaylistResponseDTO(playlists, totalLength);
    }

    public void addPlaylist(PlaylistDTO playlist, int userID) {
        playlist.setOwnerID(userID);
        playlistDAO.insert(playlist);
    }

    public void editPlaylist(PlaylistDTO playist, int id) {
        playlistDAO.update(playist, id);
    }

    public void deletePlaylist(int id) {
        playlistDAO.delete(id);
    }

    public TrackResponseDTO getTracksPerPlaylist(int id) {
        return trackService.getTracksPerPlaylist(id);
    }

    public TrackResponseDTO addTrackToPlaylist(int id, TrackDTO trackDTO) {
        return new TrackResponseDTO();
    }

    public TrackResponseDTO removeTrackFromPlaylist(int playlistId, int trackId) {
        return trackService.removeTrackFromPlaylist(playlistId, trackId);
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }
}
