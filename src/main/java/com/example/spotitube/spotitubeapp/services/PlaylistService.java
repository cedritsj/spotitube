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
    private ConnectionManager connectionManager;

    @Inject
    private PlaylistDAO playlistDAO;

    public PlaylistResponseDTO getAllPlaylists(String token) {
        try {
            Connection conn = connectionManager.startConn();
            ArrayList playlists = playlistDAO.returnPlaylists(token);
            connectionManager.closeConn();
            return new PlaylistResponseDTO(playlists, playlistDAO.returnPlaylistLength(playlists, conn));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
