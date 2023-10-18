package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.resources.TrackResource;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrackDAO extends BaseDAO<TrackDTO>{
    @Override
    public ArrayList<TrackDTO> buildFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        while (rs.next()) {
            TrackDTO track = new TrackDTO(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("performer"),
                    rs.getInt("duration"),
                    rs.getString("album"),
                    rs.getInt("playcount"),
                    rs.getString("publicationDate"),
                    rs.getString("description"),
                    rs.getBoolean("offlineAvailable"));
            tracks.add(track);
        }
        return tracks;
    }

    @Override
    public PreparedStatement statementBuilder(Connection connection, String action, Optional<TrackDTO> trackDTO, Optional<Integer> id) {
        try {
            if(action.equals("UPDATE")) {
                PreparedStatement statement = connection.prepareStatement("UPDATE tracks SET offlineAvailable = ? WHERE id = ?;");
                statement.setBoolean(1, trackDTO.get().getOfflineAvailable());
                statement.setInt(2, id.get());
                return statement;
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }

    public ArrayList<TrackDTO> getTracksFromPlaylist(Connection connection, int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT t.* FROM tracks t JOIN tracks_in_playlist tip ON t.id = tip.track_id WHERE tip.playlist_id = ?;");
            statement.setInt(1, id);
            return buildFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ArrayList<TrackDTO> getTracksNotInPlaylist(Connection conn, int id) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM tracks WHERE id NOT IN (SELECT track_id FROM tracks_in_playlist WHERE playlist_id = ?)");
            statement.setInt(1, id);
            return buildFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void insertTrackInPlaylist(Connection connection, int id, TrackDTO trackDTO) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tracks_in_playlist (playlist_id, track_id) VALUES (?, ?);");
            statement.setInt(1, id);
            statement.setInt(2, trackDTO.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void deleteTracksFromPlaylist(Connection conn, int id, int trackId) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM tracks_in_playlist WHERE playlist_id = ? AND track_id = ?;");
            statement.setInt(1, id);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
