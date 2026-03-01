package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.model.Player;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Verifiziert die SQL-Zugriffe der Player-DAO-Implementierung.
 */
public class SQLitePlayerDaoTest {

	/**
	 * Erstellt eine DAO mit gemockter {@link SQLiteDB}-Verbindung.
	 *
	 * @param connection Mock-Verbindung
	 * @return DAO-Instanz für Tests
	 */
	private SQLitePlayerDao createDao(Connection connection) {
		try (MockedStatic<SQLiteDB> mocked = mockStatic(SQLiteDB.class)) {
			mocked.when(SQLiteDB::getConnection).thenReturn(connection);
			return new SQLitePlayerDao();
		}
	}

	/**
	 * Prüft den Erfolgsfall bei der Löschung eines Spielers.
	 *
	 * @throws Exception bei Mock-Fehlern
	 */
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

	/**
	 * Prüft Fehlerbehandlung bei SQL-Ausnahme während der Löschung.
	 *
	 * @throws Exception bei Mock-Fehlern
	 */
	@Test
	void deletePlayer_sqlException_returnsFalse() throws Exception {
		Connection connection = mock(Connection.class);

		when(connection.prepareStatement("DELETE FROM player WHERE mid = ?")).thenThrow(new SQLException("fail"));

		SQLitePlayerDao dao = createDao(connection);

		boolean result = dao.deletePlayer(new Player(1, "P", "p@x.com", null));

		assertFalse(result);
	}

	/**
	 * Prüft leere Ergebnislisten als Grenzfall.
	 *
	 * @throws Exception bei Mock-Fehlern
	 */
	@Test
	void getAllPlayers_emptyResult_boundary() throws Exception {
		Connection connection = mock(Connection.class);
		Statement ps = mock(Statement.class);
		ResultSet rs = mock(ResultSet.class);

		when(connection.createStatement()).thenReturn(ps);
		when(ps.executeQuery("SELECT * FROM player")).thenReturn(rs);
		when(rs.next()).thenReturn(false);

		SQLitePlayerDao dao = createDao(connection);

		List<Player> players = dao.getAllPlayers();

		assertNotNull(players);
		assertTrue(players.isEmpty());
	}

	/**
	 * Prüft Fehlerbehandlung bei SQL-Ausnahme beim Lesen.
	 *
	 * @throws Exception bei Mock-Fehlern
	 */
	@Test
	void getAllPlayers_sqlException_returnsEmpty() throws Exception {
		Connection connection = mock(Connection.class);

		when(connection.createStatement()).thenThrow(new SQLException("fail"));

		SQLitePlayerDao dao = createDao(connection);

		List<Player> players = dao.getAllPlayers();

		assertNotNull(players);
		assertTrue(players.isEmpty());
	}
}
