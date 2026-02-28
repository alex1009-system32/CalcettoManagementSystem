// File: TeamMakerTest.java
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

import static org.junit.jupiter.api.Assertions.*;

public class TeamMakerTest {

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
	void makeTeams_createsTeamsAndAssignsPlayers() throws Exception {
		SQLiteTournamentDao tournamentDao = new SQLiteTournamentDao();
		Tournament tournament = tournamentDao.getTournamentById(1);

		int teamsBefore = countTeams();
		int distinctTeamsBefore = countDistinctTeamsForTournamentPlayers(tournament.getTid());

		new TeamMaker().makeTeams(tournament);

		int teamsAfter = countTeams();
		int distinctTeamsAfter = countDistinctTeamsForTournamentPlayers(tournament.getTid());

		assertTrue(teamsAfter > teamsBefore);
		assertTrue(distinctTeamsAfter >= distinctTeamsBefore);
	}

	@Test
	void makeTeams_boundarySingleTeamSizeStillWorks() throws Exception {
		SQLiteTournamentDao tournamentDao = new SQLiteTournamentDao();
		Tournament tournament = new Tournament(99, "SingleTeamSize", java.time.LocalDate.now(), 1, 0, 0, 1);
		insertTournament(tournament);

		insertPlayer(201, "P1", "p1@x.com", tournament.getTid(), null);
		insertPlayer(202, "P2", "p2@x.com", tournament.getTid(), null);

		int teamsBefore = countTeams();

		new TeamMaker().makeTeams(tournament);

		int teamsAfter = countTeams();
		assertTrue(teamsAfter >= teamsBefore + 2);
	}

	private int countTeams() throws Exception {
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS c FROM team")) {
			if (rs.next()) {
				return rs.getInt("c");
			}
		}
		return 0;
	}

	private int countDistinctTeamsForTournamentPlayers(int tournamentId) throws Exception {
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT COUNT(DISTINCT tid) AS c FROM player WHERE trid = " + tournamentId)) {
			if (rs.next()) {
				return rs.getInt("c");
			}
		}
		return 0;
	}

	private void insertTournament(Tournament tournament) throws Exception {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("INSERT INTO tournament (tid, tournament_name, start_date, duration, pre_round, current_round, max_team_size) VALUES (" + tournament.getTid() + ", '" + tournament.getTournamentName() + "', '2025-01-01', " + tournament.getDuration() + ", " + tournament.getPreRound() + ", " + tournament.getCurrendRound() + ", " + tournament.getMaxTeamSize() + ")");
		}
	}

	private void insertPlayer(int pid, String name, String email, int tournamentId, Integer teamId) throws Exception {
		String tidValue = teamId == null ? "NULL" : teamId.toString();
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("INSERT INTO player (pid, pname, pemail, tid, trid) VALUES (" + pid + ", '" + name + "', '" + email + "', " + tidValue + ", " + tournamentId + ")");
		}
	}

	private void setStaticConnection(Connection conn) throws Exception {
		Field field = SQLiteDB.class.getDeclaredField("connection");
		field.setAccessible(true);
		field.set(null, conn);
	}
}