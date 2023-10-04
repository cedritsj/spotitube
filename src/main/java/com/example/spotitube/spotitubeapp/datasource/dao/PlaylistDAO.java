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

    private ArrayList<PlaylistDTO> playlists = new ArrayList<>();

    @Inject
    private ConnectionManager connectionManager;

    @Inject
    private PlaylistDTO playlistDTO;

    public ArrayList returnPlaylists(String token) throws SQLException {;
        PreparedStatement statement = getAllPlaylists(connectionManager.startConn(), token);
        ResultSet results = statement.executeQuery();
        while(results.next()) {
            PlaylistDTO playlist = new PlaylistDTO(
                    results.getInt("id"),
                    results.getString("name"),
                    Objects.equals(token, results.getString("token")));
            playlists.add(playlist);
        }
        return playlists;
    }

    public int returnPlaylistLength(ArrayList<PlaylistDTO> playlists) throws SQLException {
        int duration = 0;
        Connection conn = connectionManager.startConn();
        for (PlaylistDTO playlist : playlists) {
            PreparedStatement statement = getLengthOfPlaylist(conn);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                duration += results.getInt("sum_playlist_duration");
            }
        }
        conn.close();
        return duration;
    }

    private PreparedStatement getAllPlaylists(Connection conn, String token) throws SQLException {
        int id = getOwnerIdWithToken(conn, token);
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM playlists p JOIN users u ON p.owner = u.id WHERE u.id = ?;");
        statement.setInt(1, id);
        return statement;
    }

    private PreparedStatement getLengthOfPlaylist(Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT SUM(duration) AS `sum_playlist_duration` FROM spotitube.tracks_in_playlist JOIN spotitube.tracks ON tracks.id = tracks_in_playlist.track_id WHERE playlist_id = ? ;");
        statement.setInt(1, playlistDTO.getId());
        return statement;
    }

    private int getOwnerIdWithToken(Connection conn, String token) throws SQLException {
        int id = 0;
        PreparedStatement statement = conn.prepareStatement("SELECT id FROM spotitube.users WHERE token = ?;");
        statement.setString(1, token);
        ResultSet results = statement.executeQuery();
        while(results.next()) {
            id = results.getInt("id");
        }
        return id;
    }
}
