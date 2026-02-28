package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.model.Player;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SQLitePlayerDaoTest {

	private SQLitePlayerDao createDao(Connection connection) {
		try (MockedStatic<SQLiteDB> mocked = mockStatic(SQLiteDB.class)) {
			mocked.when(SQLiteDB::getConnection).thenReturn(connection);
			return new SQLitePlayerDao();
		}
	}

	@Test
	void deletePlayer_success() throws Exception {
		Connection connection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);

		when(connection.prepareStatement("DELETE FROM player WHERE mid = ?")).thenReturn(ps);

		SQLitePlayerDao dao = createDao(connection);

		boolean result = dao.deletePlayer(new Player(1, "P", "p@x.com", null));

		assertTrue(result);
		verify(ps).execute();
	}

	@Test
	void deletePlayer_sqlException_returnsFalse() throws Exception {
		Connection connection = mock(Connection.class);

		when(connection.prepareStatement("DELETE FROM player WHERE mid = ?")).thenThrow(new SQLException("fail"));

		SQLitePlayerDao dao = createDao(connection);

		boolean result = dao.deletePlayer(new Player(1, "P", "p@x.com", null));

		assertFalse(result);
	}

	@Test
	void getAllPlayers_emptyResult_boundary() throws Exception {
		Connection connection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(connection.prepareStatement("SELECT * FROM player")).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false);

		SQLitePlayerDao dao = createDao(connection);

		List<Player> players = dao.getAllPlayers();

		assertNotNull(players);
		assertTrue(players.isEmpty());
	}

	@Test
	void getAllPlayers_sqlException_returnsEmpty() throws Exception {
		Connection connection = mock(Connection.class);

		when(connection.prepareStatement(anyString())).thenThrow(new SQLException("fail"));

		SQLitePlayerDao dao = createDao(connection);

		List<Player> players = dao.getAllPlayers();

		assertNotNull(players);
		assertTrue(players.isEmpty());
	}
}