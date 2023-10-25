package com.example.spotitube.spotitubeapp.resources.interfaces;

import com.example.spotitube.spotitubeapp.resources.dto.response.TrackResponseDTO;

public interface ITrackService {
    TrackResponseDTO getTracksNotInPlaylist(int id);
}
