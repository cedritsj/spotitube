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

    public TrackResponseDTO getTracksNotInPlaylist(int id) {
        TrackResponseDTO trackResponseDTO = new TrackResponseDTO();
        trackResponseDTO.setTracks(trackDAO.getTracksNotInPlaylist(id));
        return trackResponseDTO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
