package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistService {

    @Inject
    private PlaylistDAO playlistDAO;

    public PlaylistResponseDTO getAllPlaylists(int userID) {
        try {
            ArrayList<PlaylistDTO> playlists = playlistDAO.getAll();
            int totalLength = 0;
            for (PlaylistDTO playlist : playlists) {
                playlist.setOwner(userID == playlist.getOwnerID());
                totalLength += playlist.getLength();
            }
            return new PlaylistResponseDTO(playlists, totalLength);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPlaylist(PlaylistDTO playlist, int userID) {
        try {
            playlist.setOwnerID(userID);
            playlistDAO.insert(playlist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editPlaylist(PlaylistDTO playlist) {
        try {
            playlistDAO.update(playlist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlaylist(PlaylistDTO playlist) {
        try {
            playlistDAO.delete(playlist.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
