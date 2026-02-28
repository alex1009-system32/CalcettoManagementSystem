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

public class MatchMakerTest {

	private Connection connection;

	@BeforeEach
	void setUp() throws Exception {
		connection = DriverManager.getConnection("jdbc:sqlite::memory:");
		setStaticConnection(connection);
		SQLiteDB.initTest();
	}

	@AfterEach
	void tearDown() throws Exception {
		if (connection != null) {
			connection.close();
		}
		setStaticConnection(null);
	}

	@Test
	void makePreRounds_returnsImmediatelyWhenCurrentRoundNotZero() {
		Tournament tournament = mock(Tournament.class);
		when(tournament.getCurrendRound()).thenReturn(1);

		new MatchMaker().makePreRounds(tournament);

		verify(tournament).getCurrendRound();
		verifyNoMoreInteractions(tournament);
	}

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

	private int countMatchesForTournament(int tournamentId) throws Exception {
		try (Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS c FROM \"match\" WHERE tid = " + tournamentId)) {
			if (rs.next()) {
				return rs.getInt("c");
			}
		}
		return 0;
	}

	private int getTournamentCurrentRound(int tournamentId) throws Exception {
		try (Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery("SELECT current_round FROM tournament WHERE tid = " + tournamentId)) {
			if (rs.next()) {
				return rs.getInt("current_round");
			}
		}
		return 0;
	}

	private void setStaticConnection(Connection conn) throws Exception {
		Field field = SQLiteDB.class.getDeclaredField("connection");
		field.setAccessible(true);
		field.set(null, conn);
	}
}