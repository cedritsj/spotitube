package com.example.spotitube.spotitubeapp.services;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistService {

    @Inject
    private ConnectionManager connectionManager;

    @Inject
    private PlaylistDAO playlistDAO;

    public PlaylistResponseDTO getAllPlaylists(String token) {
        try {
            connectionManager.startConn();
            ArrayList<PlaylistDTO> playlists = playlistDAO.returnPlaylists(token);
            connectionManager.closeConn();
            return new PlaylistResponseDTO(playlists, calculateLength(playlists));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private int calculateLength(ArrayList<PlaylistDTO> playlists) {
        return playlists.size();
    }
}
