package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Verifiziert die SQL-Zugriffe der Turnier-DAO-Implementierung.
 */
public class SQLiteTournamentDaoTest {

	/**
	 * Erstellt eine DAO mit gemockter {@link SQLiteDB}-Verbindung.
	 *
	 * @param connection Mock-Verbindung
	 * @return DAO-Instanz für Tests
	 */
	private SQLiteTournamentDao createDao(Connection connection) {
		try (MockedStatic<SQLiteDB> mocked = mockStatic(SQLiteDB.class)) {
			mocked.when(SQLiteDB::getConnection).thenReturn(connection);
			return new SQLiteTournamentDao();
		}
	}

	/**
	 * Prüft den Erfolgsfall beim Fortschreiben der Runde.
	 *
	 * @throws Exception bei Mock-Fehlern
	 */
	@Test
	void increaseRound_success() throws Exception {
		Connection connection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);

		when(connection.prepareStatement("UPDATE tournament SET current_round = ? WHERE tid = ?")).thenReturn(ps);

		SQLiteTournamentDao dao = createDao(connection);

		boolean result = dao.increaseRound(new Tournament(1, "T", LocalDate.now(), 1, 1, 1, 2));

		assertTrue(result);
		verify(ps).executeUpdate();
	}

	/**
	 * Prüft Fehlerbehandlung bei SQL-Ausnahme während des Updates.
	 *
	 * @throws Exception bei Mock-Fehlern
	 */
	@Test
	void increaseRound_sqlException_returnsFalse() throws Exception {
		Connection connection = mock(Connection.class);

		when(connection.prepareStatement("UPDATE tournament SET current_round = ? WHERE tid = ?")).thenThrow(new SQLException("fail"));

		SQLiteTournamentDao dao = createDao(connection);

		boolean result = dao.increaseRound(new Tournament(1, "T", LocalDate.now(), 1, 1, 1, 2));

		assertFalse(result);
	}

	/**
	 * Prüft leere Ergebnislisten als Grenzfall.
	 *
	 * @throws Exception bei Mock-Fehlern
	 */
	@Test
	void getAllTournaments_emptyResult_boundary() throws Exception {
		Connection connection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(connection.createStatement()).thenReturn(ps);
		when(ps.executeQuery("SELECT * FROM tournament")).thenReturn(rs);
		when(rs.next()).thenReturn(false);

		SQLiteTournamentDao dao = createDao(connection);

		List<Tournament> tournaments = dao.getAllTournaments();

		assertNotNull(tournaments);
		assertTrue(tournaments.isEmpty());
	}
}
