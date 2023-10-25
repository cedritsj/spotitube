package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import com.example.spotitube.spotitubeapp.resources.interfaces.IPlaylistService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.util.ArrayList;

@Default
@ApplicationScoped
public class PlaylistService implements IPlaylistService {

    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;

    @Override
    public PlaylistResponseDTO getAllPlaylists(int userID) {
        ArrayList<PlaylistDTO> playlists = playlistDAO.getAll();
        for (PlaylistDTO playlist : playlists) {
            playlist.setOwner(userID == playlist.getOwnerID());
            playlist.setTracks(getTracksPerPlaylist(playlist.getId()).getTracks());
            for (TrackDTO track : playlist.getTracks()) {
                int length = playlist.getLength();
                playlist.setLength(length + track.getDuration());
            }
        }
        return new PlaylistResponseDTO(playlists, playlists.stream().mapToInt(PlaylistDTO::getLength).sum());
    }

    @Override
    public void addPlaylist(PlaylistDTO playlist, int userID) {
        playlist.setOwnerID(userID);
        playlistDAO.insert(playlist);
    }

    @Override
    public void editPlaylist(PlaylistDTO playlist, int id) {
        playlistDAO.update(playlist, id);
    }

    @Override
    public void deletePlaylist(int id) {
        playlistDAO.delete(id);
    }

    @Override
    public TrackResponseDTO getTracksPerPlaylist(int id) {
        return new TrackResponseDTO(trackDAO.getAllTracksInPlaylist(id));
    }

    @Override
    public void addTrackToPlaylist(TrackDTO trackDTO, int playlistId) {
        trackDAO.update(trackDTO, trackDTO.getId());
        trackDAO.insertTrackInPlaylist(trackDTO, playlistId);
    }

    @Override
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
