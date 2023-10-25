package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;
import com.example.spotitube.spotitubeapp.resources.interfaces.ITrackService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

@Default
@ApplicationScoped
public class TrackService implements ITrackService {

    private TrackDAO trackDAO;

    @Override
    public TrackResponseDTO getTracksNotInPlaylist(int id) {
        TrackResponseDTO trackResponseDTO = new TrackResponseDTO();
        trackResponseDTO.setTracks(trackDAO.getAllTracksNotInPlaylist(id));
        return trackResponseDTO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
