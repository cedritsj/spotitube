package DAOs;

import com.example.spotitube.spotitubeapp.datasource.dao.LoginDAO;
import com.example.spotitube.spotitubeapp.datasource.dbconnection.ConnectionManager;
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

    private UserDTO userDTO;

    private ConnectionManager connectionManager;

    @BeforeEach
    public void SetUp() {
        this.sut = mock(LoginDAO.class);
        this.connectionManager = mock(ConnectionManager.class);
        this.statement = mock(PreparedStatement.class);
        this.rs = mock(ResultSet.class);
        this.conn = mock(Connection.class);
        this.loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("Test");
        loginRequestDTO.setPassword("Test");
        this.userDTO = new UserDTO();

        when(connectionManager.startConn()).thenReturn(conn);
    }

    @Test
    void testGetUserWithLoginRequestSuccessfully() throws SQLException {

        userDTO.setId(1);
        userDTO.setUser("Test");
        userDTO.setToken("Test");

        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(userDTO);

        when(sut.getUserWithLoginRequestStatement(conn, loginRequestDTO)).thenReturn(statement);
        when(sut.buildFromResultSet(statement.executeQuery()).get(0)).thenReturn(userDTOS.get(0));

        UserDTO result = sut.getUserWithLoginRequest(loginRequestDTO);

        assertEquals(userDTOS.get(0).getUser(), result.getUser());
    }
}
