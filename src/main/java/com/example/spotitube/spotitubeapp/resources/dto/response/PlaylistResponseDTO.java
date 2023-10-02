package com.example.spotitube.spotitubeapp.resources.dto.response;

import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;

import java.util.ArrayList;

public class PlaylistResponseDTO {
    private ArrayList<PlaylistDTO> playlists;
    private int playlistLength;

    public PlaylistResponseDTO() {

    }

    public PlaylistResponseDTO(ArrayList<PlaylistDTO> playlists, int playlistLength) {
        this.playlists = playlists;
        this.playlistLength = playlistLength;
    }

    public ArrayList<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getPlaylistLength() {
        return playlistLength;
    }

    public void setPlaylistLength(int playlistLength) {
        this.playlistLength = playlistLength;
    }
}
