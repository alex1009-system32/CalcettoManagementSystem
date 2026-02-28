package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SQLiteMatchDaoTest {

	private SQLiteMatchDao createDao(Connection connection) {
		try (MockedStatic<SQLiteDB> mocked = mockStatic(SQLiteDB.class)) {
			mocked.when(SQLiteDB::getConnection).thenReturn(connection);
			return new SQLiteMatchDao();
		}
	}

	@Test
	void addTeamToMatch_success() throws Exception {
		Connection connection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);

		when(connection.prepareStatement("INSERT INTO team_match(tid, mid) VALUES (?, ?)")).thenReturn(ps);

		SQLiteMatchDao dao = createDao(connection);

		boolean result = dao.addTeamToMatch(new Team(1, "A"), new Match(2, 1));

		assertTrue(result);
		verify(ps).setInt(1, 1);
		verify(ps).setInt(2, 2);
		verify(ps).execute();
	}

	@Test
	void addTeamToMatch_sqlException_returnsFalse() throws Exception {
		Connection connection = mock(Connection.class);

		when(connection.prepareStatement("INSERT INTO team_match(tid, mid) VALUES (?, ?)"))
				.thenThrow(new SQLException("fail"));

		SQLiteMatchDao dao = createDao(connection);

		boolean result = dao.addTeamToMatch(new Team(1, "A"), new Match(2, 1));

		assertFalse(result);
	}

	@Test
	void getAllMatchesFromTournament_emptyResult_boundary() throws Exception {
		Connection connection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		PreparedStatement innerPs = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(connection.prepareStatement("SELECT * FROM \"match\" WHERE tid = ?")).thenReturn(ps);
		when(connection.prepareStatement("SELECT * FROM team_match WHERE mid = ?")).thenReturn(innerPs);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false);

		SQLiteMatchDao dao = createDao(connection);

		List<Match> matches = dao.getAllMatchesFromTournament(new Tournament(1, "T", java.time.LocalDate.now(), 1, 1, 0, 2));

		assertNotNull(matches);
		assertTrue(matches.isEmpty());
	}

	@Test
	void addAddPointToTeamInMatch_sqlException_returnsFalse() throws Exception {
		Connection connection = mock(Connection.class);

		when(connection.prepareStatement("UPDATE team_match SET points = ? WHERE tid = ? AND mid = ?"))
				.thenThrow(new SQLException("fail"));

		SQLiteMatchDao dao = createDao(connection);

		boolean result = dao.addAddPointToTeamInMatch(new Team(1, "A"), new Match(2, 1), 3.5);

		assertFalse(result);
	}
}