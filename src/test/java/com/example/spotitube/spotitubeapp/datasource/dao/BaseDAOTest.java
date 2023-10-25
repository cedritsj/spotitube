package com.example.spotitube.spotitubeapp.datasource.dao;

import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BaseDAOTest {
    private BaseDAO<PlaylistDTO> sut;

    private ConnectionManager connectionManager;

    private Connection connection;

    private ResultSet resultSet;

    private PreparedStatement statement;

    private ArrayList<PlaylistDTO> playlists = new ArrayList<>();

    private PlaylistDTO playlistDTO = new PlaylistDTO();

    @BeforeEach
    public void Setup() throws SQLException {
        sut = mock(BaseDAO.class, Answers.CALLS_REAL_METHODS);
        this.connectionManager = mock(ConnectionManager.class);
        this.connection = mock(Connection.class);
        this.statement = mock(PreparedStatement.class);
        this.resultSet = mock(ResultSet.class);

        playlistDTO.setId(1);
        playlistDTO.setName("playlist");
        playlistDTO.setOwner(true);
        playlistDTO.setOwnerID(1);
        playlists.add(playlistDTO);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        sut.setConnectionManager(connectionManager);
    }

    @Test
    void testGetAllSuccessFully() throws SQLException {
        when(sut.statementBuilder(connection, "SELECT", Optional.empty(), Optional.empty())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(sut.buildFromResultSet(resultSet)).thenReturn(playlists);

        ArrayList<PlaylistDTO> result = sut.getAll();

        assertEquals(playlists, result);
    }

    @Test
    void testGetSuccessfully() throws SQLException {
        when(sut.statementBuilder(connection, "SELECT", Optional.empty(), Optional.of(playlistDTO.getId()))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(sut.buildFromResultSet(resultSet)).thenReturn(playlists);

        PlaylistDTO result = sut.get(playlistDTO.getId());

        assertEquals(playlists.get(0), result);
    }

    @Test
    void testinsertSuccessfully() throws SQLException {
        when(sut.statementBuilder(connection, "INSERT", Optional.of(playlistDTO), Optional.empty())).thenReturn(statement);

        sut.insert(playlistDTO);

        verify(statement, times(1)).executeUpdate();
    }

    @Test
    void testUpdateSuccessfully() throws SQLException {
        when(sut.statementBuilder(connection, "UPDATE", Optional.of(playlistDTO), Optional.of(playlistDTO.getId()))).thenReturn(statement);

        sut.update(playlistDTO, playlistDTO.getId());

        verify(statement, times(1)).executeUpdate();
    }

    @Test
    void testDeleteSuccessfully() throws SQLException {
        when(sut.statementBuilder(connection, "DELETE", Optional.empty(), Optional.of(playlistDTO.getId()))).thenReturn(statement);

        sut.delete(playlistDTO.getId());

        verify(statement, times(1)).executeUpdate();
    }
}
