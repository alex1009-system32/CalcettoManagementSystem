package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testet die Match-Erstellung unter kontrollierten Datenbankbedingungen.
 */
public class MatchMakerTest {

	/**
	 * In-Memory-Verbindung für reproduzierbare Tests.
	 */
	private Connection connection;

	/**
	 * Initialisiert die Testdatenbank und bindet sie an {@link SQLiteDB}.
	 *
	 * @throws Exception bei Fehlern im Setup
	 */
	@BeforeEach
	void setUp() throws Exception {
		connection = DriverManager.getConnection("jdbc:sqlite::memory:");
		setStaticConnection(connection);
		SQLiteDB.initTest();
	}

	/**
	 * Räumt Ressourcen und statische Verbindungen auf.
	 *
	 * @throws Exception bei Fehlern im Teardown
	 */
	@AfterEach
	void tearDown() throws Exception {
		if (connection != null) {
			connection.close();
		}
		setStaticConnection(null);
	}

	/**
	 * Prüft, dass bei bereits gestarteten Turnieren keine Vorrunden erzeugt werden.
	 */
	@Test
	void makePreRounds_returnsImmediatelyWhenCurrentRoundNotZero() {
		Tournament tournament = mock(Tournament.class);
		when(tournament.getCurrendRound()).thenReturn(1);

		new MatchMaker().makePreRounds(tournament);

		verify(tournament).getCurrendRound();
		verifyNoMoreInteractions(tournament);
	}

	/**
	 * Prüft, dass Matches erstellt und die Runde fortgeschrieben wird.
	 *
	 * @throws Exception bei Datenbankfehlern
	 */
	@Test
	void makeMatchesForRound_increasesRoundAndAddsMatches() throws Exception {
		SQLiteTournamentDao tournamentDao = new SQLiteTournamentDao();
		Tournament tournament = tournamentDao.getTournamentById(1);

		int initialRound = getTournamentCurrentRound(tournament.getTid());
		int initialMatchCount = countMatchesForTournament(tournament.getTid());

		new MatchMaker().makeMatchesForRound(tournament);

		int updatedRound = getTournamentCurrentRound(tournament.getTid());
		int updatedMatchCount = countMatchesForTournament(tournament.getTid());

		assertEquals(initialRound + 1, updatedRound);
		assertTrue(updatedMatchCount >= initialMatchCount);
	}

	/**
	 * Prüft das Verhalten bei Turnieren ohne Matches als Grenzfall.
	 *
	 * @throws Exception bei Datenbankfehlern
	 */
	@Test
	void makeMatchesForRound_noMatchesBoundaryStillIncreasesRound() throws Exception {
		SQLiteTournamentDao tournamentDao = new SQLiteTournamentDao();
		Tournament newTournament = tournamentDao.addTournament("NoMatches", 1, 0, 2);

		int initialRound = getTournamentCurrentRound(newTournament.getTid());
		int initialMatchCount = countMatchesForTournament(newTournament.getTid());

		new MatchMaker().makeMatchesForRound(newTournament);

		int updatedRound = getTournamentCurrentRound(newTournament.getTid());
		int updatedMatchCount = countMatchesForTournament(newTournament.getTid());

		assertEquals(initialRound + 1, updatedRound);
		assertEquals(initialMatchCount, updatedMatchCount);
	}

	/**
	 * Zählt Matches eines Turniers zur Fortschrittsprüfung.
	 *
	 * @param tournamentId Turnier-ID
	 * @return Anzahl der Matches
	 * @throws Exception bei Datenbankfehlern
	 */
	private int countMatchesForTournament(int tournamentId) throws Exception {
		try (Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS c FROM \"match\" WHERE tid = " + tournamentId)) {
			if (rs.next()) {
				return rs.getInt("c");
			}
		}
		return 0;
	}

	/**
	 * Liest die aktuelle Runde eines Turniers aus der Datenbank.
	 *
	 * @param tournamentId Turnier-ID
	 * @return aktuelle Runde
	 * @throws Exception bei Datenbankfehlern
	 */
	private int getTournamentCurrentRound(int tournamentId) throws Exception {
		try (Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery("SELECT current_round FROM tournament WHERE tid = " + tournamentId)) {
			if (rs.next()) {
				return rs.getInt("current_round");
			}
		}
		return 0;
	}

	/**
	 * Überschreibt die statische Verbindung von {@link SQLiteDB} für Tests.
	 *
	 * @param conn Testverbindung
	 * @throws Exception bei Reflektionsfehlern
	 */
	private void setStaticConnection(Connection conn) throws Exception {
		Field field = SQLiteDB.class.getDeclaredField("connection");
		field.setAccessible(true);
		field.set(null, conn);
	}
}
