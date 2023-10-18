package DAOs;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
import com.example.spotitube.spotitubeapp.resources.dto.request.LoginRequestDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LoginDAOTest {

    private LoginDAO sut;

    private PreparedStatement statement;

    private ResultSet rs;

    private Connection conn;

    private LoginRequestDTO loginRequestDTO;

    private ConnectionManager connectionManager;

    @BeforeEach
    public void SetUp() {
        this.sut = mock(LoginDAO.class);
        this.connectionManager = mock(ConnectionManager.class);
        this.statement = mock(PreparedStatement.class);
        this.rs = mock(ResultSet.class);
        this.conn = mock(Connection.class);
        this.loginRequestDTO = mock(LoginRequestDTO.class);

        loginRequestDTO.setUser("user");
        loginRequestDTO.setPassword("password");

        when(connectionManager.startConn()).thenReturn(conn);

    }

//    @Test
//    void testExistingUserSuccessfullyReturnsTrue() throws SQLException {
//
//        loginRequestDTO.setUser("user");
//        loginRequestDTO.setPassword("password");
//
//        when(sut.getUserWithStatement(conn, loginRequestDTO)).thenReturn(statement);
//        when(statement.executeQuery()).thenReturn(rs);
//        when(rs.next()).thenReturn(true, false);
//        when(sut.hasSingleResult(rs)).thenReturn(true);
//        when(rs.getString("user")).thenReturn("user");
//        when(loginRequestDTO.getPassword()).thenReturn("password");
//
//        boolean result = sut.existingUser(loginRequestDTO);
//
//        assertTrue(result);
//    }
}
