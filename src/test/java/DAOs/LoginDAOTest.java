package DAOs;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.exceptions.AuthenticationException;
import com.example.spotitube.spotitubeapp.exceptions.DatabaseException;
import com.example.spotitube.spotitubeapp.resources.dto.UserDTO;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginDAOTest {

    private LoginDAO sut;

    private PreparedStatement statement;

    private ResultSet rs;

    private Connection conn;

    private LoginRequestDTO loginRequestDTO;

    private UserDTO userDTO;

    private ArrayList<UserDTO> userDTOS;

    private ConnectionManager connectionManager;

    @BeforeEach
    public void SetUp() {
        this.sut = new LoginDAO();
        this.connectionManager = mock(ConnectionManager.class);
        this.statement = mock(PreparedStatement.class);
        this.rs = mock(ResultSet.class);
        this.conn = mock(Connection.class);
        this.loginRequestDTO = new LoginRequestDTO("test", "test");
        this.userDTO = new UserDTO(1, "test", "test");

        userDTOS = new ArrayList<>();
        userDTOS.add(userDTO);

        sut.setConnectionManager(connectionManager);
    }

    @Test
    void testGetUserWithLoginRequestSuccessfully() throws SQLException {
        when(connectionManager.startConn()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getString("user")).thenReturn(userDTO.getUser());
        when(rs.getString("token")).thenReturn(userDTO.getToken());
        when(rs.getInt("id")).thenReturn(userDTO.getId());

        UserDTO result = sut.getUserWithLoginRequest(loginRequestDTO);

        verify(connectionManager).closeConn();
        assertEquals(userDTOS.get(0).getUser(), result.getUser());
    }

    @Test
    void testGetUserWithLoginRequestStatementSuccessfully() throws SQLException {
        when(connectionManager.startConn()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        PreparedStatement result = sut.getUserWithLoginRequestStatement(conn, loginRequestDTO);

        verify(conn).prepareStatement("SELECT * FROM users WHERE user = ? AND password = ?;");
        verify(statement).setString(1, loginRequestDTO.getUser());
        verify(statement).setString(2, DigestUtils.sha256Hex(loginRequestDTO.getPassword()));
        assertEquals(statement, result);
    }

    @Test
    void testGetUserWithTokenSuccessfully() throws SQLException {
        String token = userDTO.getToken();

        when(connectionManager.startConn()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getString("user")).thenReturn(userDTO.getUser());
        when(rs.getString("token")).thenReturn(userDTO.getToken());
        when(rs.getInt("id")).thenReturn(userDTO.getId());

        UserDTO result = sut.getUserWithToken(token).get();

        verify(connectionManager).closeConn();
        assertEquals(userDTO.getToken(), result.getToken());
    }

    @Test
    void testGetUserWithTokenStatementSuccessfully() throws SQLException {
        String token = userDTO.getToken();

        when(connectionManager.startConn()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        PreparedStatement result = sut.getUserWithTokenStatement(conn, token);

        verify(conn).prepareStatement("SELECT * FROM users WHERE token = ?;");
        verify(statement).setString(1, token);
        assertEquals(statement, result);
    }

    @Test
    void testStatementBuilderUpdate() throws SQLException {
        when(connectionManager.startConn()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(statement);

        PreparedStatement result = sut.statementBuilder(conn, "UPDATE", Optional.of(userDTO), Optional.of(userDTO.getId()));

        verify(conn).prepareStatement("UPDATE users SET token = ? WHERE user = ?;");
        verify(statement).setString(1, userDTO.getToken());
        verify(statement).setString(2, userDTO.getUser());
        assertEquals(statement, result);
    }
}
