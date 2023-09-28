package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO{

    private ArrayList<PlaylistDTO> playlists;

    @Inject
    private ConnectionManager connectionManager;

    @Inject
    private PlaylistDTO playlistDTO;

    public ArrayList returnPlaylists() throws SQLException {
        PreparedStatement statement = getAllPlaylists(connectionManager.startConn(), playlistDTO);
        ResultSet results = statement.executeQuery();
        while(results.next()) {
            //playlists.add();
        }
        return playlists;
    }

    public PreparedStatement getAllPlaylists(Connection conn, PlaylistDTO playlistDTO) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM spotitube.playlists WHERE id = ?");
        statement.setInt(1, playlistDTO.getId());
        conn.close();
        return statement;
    }
}
