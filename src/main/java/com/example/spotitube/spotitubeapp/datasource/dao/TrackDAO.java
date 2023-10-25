package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class TrackDAO extends BaseDAO<TrackDTO> {

    public ArrayList<TrackDTO> getAllTracksInPlaylist(int id) {
        try {
            PreparedStatement statement = statementBuilder(getConnection(), "SELECT", Optional.empty(), Optional.of(id));
            ArrayList<TrackDTO> result = buildFromResultSet(statement.executeQuery());
            closeConnection();
            return result;
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public void insertTrackInPlaylist(TrackDTO trackDTO, int playlistId) {
        try {
            PreparedStatement statement = statementBuilder(getConnection(), "INSERT", Optional.of(trackDTO), Optional.of(playlistId));
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public void deleteTrackFromPlaylist(int trackId, int playlistId) {
        try {
            TrackDTO trackDTO = new TrackDTO(trackId);
            PreparedStatement statement = statementBuilder(getConnection(), "DELETE", Optional.of(trackDTO), Optional.of(playlistId));
            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    public ArrayList<TrackDTO> getAllTracksNotInPlaylist(int id) {
        try {
            PreparedStatement statement = getAllTracksNotInPlaylistStatement(id);
            ArrayList<TrackDTO> result = buildFromResultSet(statement.executeQuery());
            closeConnection();
            return result;
        } catch (SQLException e) { throw new DatabaseException(e.getMessage()); }
    }

    @Override
    public PreparedStatement statementBuilder(Connection connection, String action, Optional<TrackDTO> trackDTO, Optional<Integer> id) throws SQLException {
        if (id.isEmpty() && !action.equals("UPDATE")) {
            return null;
        }

        if (trackDTO.isEmpty() && !action.equals("SELECT")) {
            return null;
        }

        switch (action) {
            case "SELECT" -> {
                PreparedStatement statement = getConnection().prepareStatement("SELECT t.* FROM tracks t JOIN tracks_in_playlist tip ON t.id = tip.track_id WHERE tip.playlist_id = ?;");
                statement.setInt(1, id.get());
                return statement;
            }
            case "UPDATE" -> {
                PreparedStatement statement = connection.prepareStatement("UPDATE tracks SET offlineAvailable = ? WHERE id = ?;");
                statement.setBoolean(1, trackDTO.get().getOfflineAvailable());
                statement.setInt(2, trackDTO.get().getId());
                return statement;
            }
            case "INSERT" -> {
                PreparedStatement statement = getConnection().prepareStatement("INSERT INTO tracks_in_playlist (playlist_id, track_id) VALUES (?, ?);");
                statement.setInt(1, id.get());
                statement.setInt(2, trackDTO.get().getId());
                return statement;
            }
            case "DELETE" -> {
                PreparedStatement statement = getConnection().prepareStatement("DELETE FROM tracks_in_playlist WHERE playlist_id = ? AND track_id = ?;");
                statement.setInt(1, id.get());
                statement.setInt(2, trackDTO.get().getId());
                return statement;
            }
        }
        return null;
    }

    private PreparedStatement getAllTracksNotInPlaylistStatement(int id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM tracks WHERE id NOT IN (SELECT track_id FROM tracks_in_playlist WHERE playlist_id = ?)");
        statement.setInt(1, id);
        return statement;
    }

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
        rs.close();
        return tracks;
    }
}
