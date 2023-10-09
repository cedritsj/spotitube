package com.example.spotitube.spotitubeapp.resources.dto.response;

import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;

import java.util.ArrayList;
import java.util.List;

public class TrackResponseDTO {

    private List<TrackDTO> tracks = new ArrayList<>();
    private int length;

    public TrackResponseDTO() {
    }

    public TrackResponseDTO(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
