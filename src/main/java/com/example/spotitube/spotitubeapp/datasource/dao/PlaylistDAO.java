package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.response.PlaylistResponseDTO;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class PlaylistDAO {

    private ArrayList<PlaylistDTO> playlists = new ArrayList<>();

    @Inject
    private ConnectionManager connectionManager;

    public PlaylistResponseDTO returnPlaylists(String token) throws SQLException {
        PreparedStatement statement = getAllPlaylists(connectionManager.startConn(), token);
        ResultSet results = statement.executeQuery();
        int totalLength = 0;
        while (results.next()) {
            PlaylistDTO playlist = new PlaylistDTO(
                    results.getInt("id"),
                    results.getString("name"),
                    Objects.equals(token, results.getString("token")));
            playlists.add(playlist);
            totalLength += results.getInt("playlist_duration");
        }
        return new PlaylistResponseDTO(playlists, totalLength);
    }

    private int getOwnerIdWithToken(Connection conn, String token) throws SQLException {
        int id = 0;
        PreparedStatement statement = conn.prepareStatement("SELECT id FROM spotitube.users WHERE token = ?;");
        statement.setString(1, token);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            id = results.getInt("id");
        }
        return id;
    }

    public void addPlaylist(String token, PlaylistDTO playlist) {
        try {
            Connection conn = connectionManager.startConn();
            PreparedStatement statement = insertIntoPlaylists(conn, token, playlist);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement getAllPlaylists(Connection conn, String token) throws SQLException {
        int id = getOwnerIdWithToken(conn, token);
        PreparedStatement statement = conn.prepareStatement("SELECT p.*, u.token, SUM(t.duration) AS `playlist_duration`" +
                "FROM playlists p" +
                "         LEFT JOIN spotitube.tracks_in_playlist tip ON tip.playlist_id = p.id" +
                "         LEFT JOIN spotitube.tracks t ON t.id = tip.track_id" +
                "         JOIN users u ON p.owner = u.id" +
                " WHERE u.id = ? GROUP BY p.id;");
        statement.setInt(1, id);
        return statement;
    }

    private PreparedStatement insertIntoPlaylists(Connection conn, String token, PlaylistDTO playlist) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO playlists (name, owner) VALUES (?, (SELECT id FROM users WHERE token = ?));");
        statement.setString(1, playlist.getName());
        statement.setString(2, token);
        return statement;
    }
}
