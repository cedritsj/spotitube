package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.util.ArrayList;

@Default
@ApplicationScoped
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
        return new TrackResponseDTO(trackDAO.getAllTracksInPlaylist(id));
    }

    public void addTrackToPlaylist(TrackDTO trackDTO, int playlistId) {
        trackDAO.update(trackDTO, trackDTO.getId());
        trackDAO.insertTrackInPlaylist(trackDTO, playlistId);
    }

    public void removeTrackFromPlaylist(int trackId, int playlistId) {
        trackDAO.deleteTrackFromPlaylist(trackId, playlistId);
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
