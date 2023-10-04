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

    public PlaylistResponseDTO getAllPlaylists(String token) {
        try {
            return playlistDAO.returnPlaylists(token);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
