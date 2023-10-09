package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.util.ArrayList;


public class TrackService {

    private TrackDAO trackDAO;

    private ConnectionManager connectionManager;

    public TrackResponseDTO getTracksPerPlaylist(int id) {
        return new TrackResponseDTO(trackDAO.getTracksFromPlaylist(getConnection(), id));
    }

    public TrackResponseDTO getTracksNotInPlaylist(int id) {
        TrackResponseDTO trackResponseDTO = new TrackResponseDTO();
        trackResponseDTO.setTracks(trackDAO.getTracksNotInPlaylist(getConnection(), id));
        return trackResponseDTO;
    }

    public TrackResponseDTO removeTrackFromPlaylist(int id, int trackId) {
        trackDAO.deleteTracksFromPlaylist(getConnection(), id, trackId);
        return getTracksPerPlaylist(id);
    }

    private Connection getConnection() {
        return connectionManager.startConn();
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}
