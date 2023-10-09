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
    public PreparedStatement statementBuilder(Connection connection, String action, Optional<TrackDTO> trackDTO, Optional<Integer> id) throws SQLException {
        return  null;
    }

    public List<TrackDTO> getTracksFromPlaylist(Connection connection, int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT t.* FROM tracks t JOIN tracks_in_playlist tip ON t.id = tip.track_id WHERE tip.playlist_id = ?;");
            statement.setInt(1, id);
            return buildFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<TrackDTO> getTracksNotInPlaylist(Connection conn, int id) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM tracks WHERE id NOT IN (SELECT track_id FROM tracks_in_playlist WHERE playlist_id = ?)");
            statement.setInt(1, id);
            return buildFromResultSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
