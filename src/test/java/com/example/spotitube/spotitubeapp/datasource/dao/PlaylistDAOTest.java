package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PlaylistDAOTest {
    private PlaylistDAO sut;

    private PreparedStatement statement;

    private ResultSet rs;

    private Connection conn;

    private PlaylistDTO playlistDTO;

    private ArrayList<PlaylistDTO> playlistDTOS;

    private ConnectionManager connectionManager;

    @BeforeEach
    public void SetUp() {
        this.sut = new PlaylistDAO();
        this.connectionManager = mock(ConnectionManager.class);
        this.statement = mock(PreparedStatement.class);
        this.rs = mock(ResultSet.class);
        this.conn = mock(Connection.class);
        this.playlistDTO = new PlaylistDTO(1, "test", 1);

        playlistDTOS = new ArrayList<>();
        playlistDTOS.add(playlistDTO);

        sut.setConnectionManager(connectionManager);
    }

    @Test
    void testStatementBuilderGetAll() throws SQLException {
        String query = "SELECT p.*, u.token " +
                "FROM playlists p" +
                "         LEFT JOIN spotitube.tracks_in_playlist tip ON tip.playlist_id = p.id" +
                "         LEFT JOIN spotitube.tracks t ON t.id = tip.track_id" +
                "         JOIN users u ON p.owner = u.id" +
                "         GROUP BY p.id;";

        when(connectionManager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        PreparedStatement result = sut.statementBuilder(conn, "SELECT", Optional.empty(), Optional.empty());

        verify(conn).prepareStatement(query);
        assertEquals(statement, result);
    }

    @Test
    void testStatementBuilderGet() throws SQLException {
        when(connectionManager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        PreparedStatement result = sut.statementBuilder(conn, "SELECT", Optional.empty(), Optional.of(playlistDTO.getId()));

        verify(conn).prepareStatement("SELECT * FROM playlists WHERE id = ?;");
        verify(statement).setInt(1, playlistDTO.getId());
        assertEquals(statement, result);
    }

    @Test
    void testStatementBuilderInsert() throws SQLException {
        when(connectionManager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        PreparedStatement result = sut.statementBuilder(conn, "INSERT", Optional.of(playlistDTO), Optional.empty());

        verify(conn).prepareStatement("INSERT INTO playlists (name, owner) VALUES (?, ?);");
        verify(statement).setString(1, playlistDTO.getName());
        verify(statement).setInt(2, playlistDTO.getOwnerID());
        assertEquals(statement, result);
    }

    @Test
    void testStatementBuilderUpdate() throws SQLException {
        when(connectionManager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        PreparedStatement result = sut.statementBuilder(conn, "UPDATE", Optional.of(playlistDTO), Optional.of(playlistDTO.getId()));

        verify(conn).prepareStatement("UPDATE playlists SET name = ? WHERE id = ?;");
        verify(statement).setString(1, playlistDTO.getName());
        verify(statement).setInt(2, playlistDTO.getId());
        assertEquals(statement, result);
    }

    @Test
    void testStatementBuilderDelete() throws SQLException {
        when(connectionManager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        PreparedStatement result = sut.statementBuilder(conn, "DELETE", Optional.empty(), Optional.of(playlistDTO.getId()));

        verify(conn).prepareStatement("DELETE FROM playlists WHERE id = ?;");
        verify(statement).setInt(1, playlistDTO.getId());
        assertEquals(statement, result);
    }

    @Test
    void testBuildFromResultset() throws SQLException {
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(playlistDTO.getId());
        when(rs.getString("name")).thenReturn(playlistDTO.getName());
        when(rs.getInt("owner")).thenReturn(playlistDTO.getOwnerID());

        ArrayList<PlaylistDTO> result = sut.buildFromResultSet(rs);

        assertEquals(playlistDTOS.get(0).getId(), result.get(0).getId());
        assertEquals(playlistDTOS.get(0).getName(), result.get(0).getName());
        assertEquals(playlistDTOS.get(0).getOwnerID(), result.get(0).getOwnerID());
    }
}
