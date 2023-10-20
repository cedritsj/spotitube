package DAOs;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.datasource.dao.TrackDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.PlaylistDTO;
import com.example.spotitube.spotitubeapp.resources.dto.TrackDTO;
import com.example.spotitube.spotitubeapp.resources.dto.UserDTO;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TrackDAOTest {

    private TrackDAO sut;

    private PreparedStatement statement;

    private ResultSet rs;

    private Connection conn;

    private TrackDTO trackDTO, notInPlaylistDTO;

    private ArrayList<TrackDTO> tracks;

    private PlaylistDTO playlistDTO;

    private ConnectionManager connectionManager;

    @BeforeEach
    public void SetUp() {
        this.sut = new TrackDAO();
        this.connectionManager = mock(ConnectionManager.class);
        this.statement = mock(PreparedStatement.class);
        this.rs = mock(ResultSet.class);
        this.conn = mock(Connection.class);
        this.playlistDTO = new PlaylistDTO(1, "test", 1);
        this.trackDTO = new TrackDTO(1, "test", "test", 1, "test", 1, "test", "test", true);

        tracks = new ArrayList<>();
        tracks.add(trackDTO);

        sut.setConnectionManager(connectionManager);
    }

    @Test
    void testGetAllTracksFromPlaylist() throws SQLException {
        when(connectionManager.startConn()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(trackDTO.getId());
        when(rs.getString("title")).thenReturn(trackDTO.getTitle());
        when(rs.getString("performer")).thenReturn(trackDTO.getPerformer());
        when(rs.getInt("duration")).thenReturn(trackDTO.getDuration());
        when(rs.getString("album")).thenReturn(trackDTO.getAlbum());
        when(rs.getInt("playcount")).thenReturn(trackDTO.getPlaycount());
        when(rs.getString("publicationDate")).thenReturn(trackDTO.getPublicationDate());
        when(rs.getString("description")).thenReturn(trackDTO.getDescription());
        when(rs.getBoolean("offlineAvailable")).thenReturn(trackDTO.getOfflineAvailable());

        ArrayList<TrackDTO> result = sut.getTracksFromPlaylist(1);

        verify(statement).setInt(1, trackDTO.getId());

        assertEquals(tracks.get(0).getId(), result.get(0).getId());
        assertEquals(tracks.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(tracks.get(0).getPerformer(), result.get(0).getPerformer());
        assertEquals(tracks.get(0).getDuration(), result.get(0).getDuration());
        assertEquals(tracks.get(0).getAlbum(), result.get(0).getAlbum());
        assertEquals(tracks.get(0).getPlaycount(), result.get(0).getPlaycount());
        assertEquals(tracks.get(0).getPublicationDate(), result.get(0).getPublicationDate());
        assertEquals(tracks.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(tracks.get(0).getOfflineAvailable(), result.get(0).getOfflineAvailable());
    }

    @Test
    void testGetTracksNotInPlaylistSuccessfully() throws SQLException {
        when(connectionManager.startConn()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(trackDTO.getId());
        when(rs.getString("title")).thenReturn(trackDTO.getTitle());
        when(rs.getString("performer")).thenReturn(trackDTO.getPerformer());
        when(rs.getInt("duration")).thenReturn(trackDTO.getDuration());
        when(rs.getString("album")).thenReturn(trackDTO.getAlbum());
        when(rs.getInt("playcount")).thenReturn(trackDTO.getPlaycount());
        when(rs.getString("publicationDate")).thenReturn(trackDTO.getPublicationDate());
        when(rs.getString("description")).thenReturn(trackDTO.getDescription());
        when(rs.getBoolean("offlineAvailable")).thenReturn(trackDTO.getOfflineAvailable());

        ArrayList<TrackDTO> result = sut.getTracksNotInPlaylist(1);

        verify(statement).setInt(1, trackDTO.getId());

        assertEquals(tracks.get(0).getId(), result.get(0).getId());
        assertEquals(tracks.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(tracks.get(0).getPerformer(), result.get(0).getPerformer());
        assertEquals(tracks.get(0).getDuration(), result.get(0).getDuration());
        assertEquals(tracks.get(0).getAlbum(), result.get(0).getAlbum());
        assertEquals(tracks.get(0).getPlaycount(), result.get(0).getPlaycount());
        assertEquals(tracks.get(0).getPublicationDate(), result.get(0).getPublicationDate());
        assertEquals(tracks.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(tracks.get(0).getOfflineAvailable(), result.get(0).getOfflineAvailable());
    }

    @Test
    void testInsertTrackIntoPlaylistSuccessfully() throws SQLException {
        when(connectionManager.startConn()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        sut.insertTrackInPlaylist(1, trackDTO);

        verify(conn).prepareStatement("INSERT INTO tracks_in_playlist (playlist_id, track_id) VALUES (?, ?);");
        verify(statement).setInt(1, playlistDTO.getId());
        verify(statement).setInt(2, trackDTO.getId());
        verify(statement).executeUpdate();
        verify(connectionManager).closeConn();
    }
}
