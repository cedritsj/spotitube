package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class PlaylistDAO{

    private ArrayList<PlaylistDTO> playlists;

    @Inject
    private ConnectionManager connectionManager;

    @Inject
    private PlaylistDTO playlistDTO;

    public ArrayList returnPlaylists(String token) throws SQLException {
        PreparedStatement statement = getAllPlaylists(connectionManager.startConn(), token);
        ResultSet results = statement.executeQuery();
        while(results.next()) {
            PlaylistDTO playlist = new PlaylistDTO(
                    results.getInt("id"),
                    results.getString("title"),
                    Objects.equals(token, results.getString("token")));
            playlists.add(playlist);
        }
        return playlists;
    }

    public PreparedStatement getAllPlaylists(Connection conn, String token) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM spotitube.playlists WHERE owner = ?");
        statement.setString(1, token);
        conn.close();
        return statement;
    }
}
