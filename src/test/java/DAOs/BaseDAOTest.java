package DAOs;

import com.example.spotitube.spotitubeapp.datasource.dao.BaseDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.PlaylistDAO;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseDAOTest {
    private PlaylistDAO sut;

    private PlaylistDTO playlistDTO;

    private ConnectionManager connectionManager;

    private Connection connection;

    private ResultSet resultSet;

    private PreparedStatement statement;

    private ArrayList<PlaylistDTO> playlists = new ArrayList<>();

    @BeforeEach
    public void Setup() throws SQLException {
        this.sut = mock(PlaylistDAO.class);
        this.connectionManager = mock(ConnectionManager.class);
        this.connection = mock(Connection.class);
        this.statement = mock(PreparedStatement.class);
        this.resultSet = mock(ResultSet.class);
        this.playlistDTO = mock(PlaylistDTO.class);

        playlistDTO.setId(1);
        playlistDTO.setName("playlist");
        playlistDTO.setOwner(true);
        playlistDTO.setOwnerID(1);
        playlists.add(playlistDTO);

        when(connectionManager.startConn()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
    }

//    @Test
//    void testGetAllSuccessFully() throws SQLException {
//        when(sut.statementBuilder(connection, "SELECT", Optional.empty(), Optional.empty())).thenReturn(statement);
//        when(sut.buildFromResultSet(statement.executeQuery())).thenReturn(playlists);
//
//        ArrayList result = sut.getAll();
//
//        assertEquals(playlists.get(0), result.get(0));
//
//
//    }
}
