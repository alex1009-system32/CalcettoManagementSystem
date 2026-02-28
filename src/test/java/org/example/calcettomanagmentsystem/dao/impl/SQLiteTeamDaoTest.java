package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.model.Player;
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

public class SQLiteTeamDaoTest {

	private SQLiteTeamDao createDao(Connection connection) {
		try (MockedStatic<SQLiteDB> mocked = mockStatic(SQLiteDB.class)) {
			mocked.when(SQLiteDB::getConnection).thenReturn(connection);
			return new SQLiteTeamDao();
		}
	}

	@Test
	void addPlayerToTeam_success() throws Exception {
		Connection connection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);

		when(connection.prepareStatement("UPDATE player SET player.tid = ? WHERE player.pid = ?")).thenReturn(ps);

		SQLiteTeamDao dao = createDao(connection);
		Team team = new Team(1, "A");
		Player player = new Player(10, "P", "p@x.com", null);

		boolean result = dao.addPlayerToTeam(player, team);

		assertTrue(result);
		assertTrue(team.containsPlayer(player));
		verify(ps).executeUpdate();
	}

	@Test
	void addPlayerToTeam_sqlException_returnsFalse() throws Exception {
		Connection connection = mock(Connection.class);

		when(connection.prepareStatement("UPDATE player SET player.tid = ? WHERE player.pid = ?"))
				.thenThrow(new SQLException("fail"));

		SQLiteTeamDao dao = createDao(connection);
		Team team = new Team(1, "A");
		Player player = new Player(10, "P", "p@x.com", null);

		boolean result = dao.addPlayerToTeam(player, team);

		assertFalse(result);
		assertFalse(team.containsPlayer(player));
	}

	@Test
	void getAllTeamsFromTournament_emptyResult_boundary() throws Exception {
		Connection connection = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		PreparedStatement innerPs = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(connection.prepareStatement("SELECT * FROM team WHERE tid IN(SELECT tid FROM player WHERE trid = ?)")).thenReturn(ps);
		when(connection.prepareStatement("select * from player where tid = ?")).thenReturn(innerPs);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false);

		SQLiteTeamDao dao = createDao(connection);

		List<Team> teams = dao.getAllTeamsFromTournament(new Tournament(1, "T", java.time.LocalDate.now(), 1, 1, 0, 2));

		assertNotNull(teams);
		assertTrue(teams.isEmpty());
	}
}