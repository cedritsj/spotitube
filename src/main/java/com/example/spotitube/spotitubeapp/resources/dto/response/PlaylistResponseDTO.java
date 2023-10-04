package com.example.spotitube.spotitubeapp.resources.dto.response;

import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;

import java.util.ArrayList;

public class PlaylistResponseDTO {
    private ArrayList<PlaylistDTO> playlists;
    private int length;

    public PlaylistResponseDTO() {

    }

    public PlaylistResponseDTO(ArrayList<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public ArrayList<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
