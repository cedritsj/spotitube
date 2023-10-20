package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistService {

    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;

    public PlaylistResponseDTO getAllPlaylists(int userID) {
        ArrayList<PlaylistDTO> playlists = playlistDAO.getAll();
        for (PlaylistDTO playlist : playlists) {
            playlist.setOwner(userID == playlist.getOwnerID());
            playlist.setTracks(getTracksPerPlaylist(playlist.getId()).getTracks());
        }
        return new PlaylistResponseDTO(playlists, playlists.stream().mapToInt(PlaylistDTO::getLength).sum());
    }

    public void addPlaylist(PlaylistDTO playlist, int userID) {
        playlist.setOwnerID(userID);
        playlistDAO.insert(playlist);
    }

    public void editPlaylist(PlaylistDTO playlist, int id) {
        playlistDAO.update(playlist, id);
    }

    public void deletePlaylist(int id) {
        playlistDAO.delete(id);
    }

    public TrackResponseDTO getTracksPerPlaylist(int id) {
        return new TrackResponseDTO(trackDAO.getTracksFromPlaylist(id));
    }

    public void addTrackToPlaylist(int playlistId, TrackDTO trackDTO) {
        trackDAO.update(trackDTO, trackDTO.getId());
        trackDAO.insertTrackInPlaylist(playlistId, trackDTO);
    }

    public void removeTrackFromPlaylist(int id, int trackId) {
        trackDAO.deleteTracksFromPlaylist(id, trackId);
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
