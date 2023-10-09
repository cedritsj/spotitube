package com.example.spotitube.spotitubeapp.resources.dto.response;

import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;

import java.util.ArrayList;
import java.util.List;

public class TrackResponseDTO {

    private ArrayList<TrackDTO> tracks = new ArrayList<>();
    private int length;

    public TrackResponseDTO() {
    }

    public TrackResponseDTO(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public void setTracks(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
