package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteMatchDao implements MatchDao {

	private Connection connection;

	public SQLiteMatchDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addMatch(Tournament tournament, int round) {
		String sql = "insert into match (round, tid) values (?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(2, tournament.getTid());
			preparedStatement.setInt(1, round);

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addTeamToMatch(Team team, double points, Match match) {
		String sql = "insert into team_match(tid, points, mid) values (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, team.getTid());
			preparedStatement.setDouble(2, points);
			preparedStatement.setInt(3, match.getMid());

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Match> getAllMatchesFromTournament(Tournament tournament) {
		String sql = "select * from match where tid = ?";
		String innerSql = "select * from team_match where mid = ?";

		Team team;
		Match match;
		List<Match> matches = new ArrayList<>();

		PreparedStatement innerPreparedStatement;
		ResultSet innerResultSet;


		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tournament.getTid());

			ResultSet resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				match = new Match(resultset.getInt("mid"), resultset.getInt("round"));

				innerPreparedStatement = connection.prepareStatement(innerSql);
				innerPreparedStatement.setInt(1, resultset.getInt("mid"));

				innerResultSet = innerPreparedStatement.executeQuery();

				while (innerResultSet.next()) {
					SQLiteTeamDao teamDao = new SQLiteTeamDao();

					team = teamDao.getTeamById(innerResultSet.getInt("tid"));

					match.addTeam(team);
					match.addPoints(team, innerResultSet.getDouble("points"));

				}

				matches.add(match);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}

	// Not Tested
	@Deprecated
	@Override
	public List<Match> getAllMatchesFromTeam(Team team) {
		String sql = "SELECT * FROM match WHERE mid IN (SELECT mid FROM team_match WHERE team_match.tid = ?)";
		String innerSql = "select * from team_match where mid = ?";

		Match match;
		List<Match> matches = new ArrayList<>();

		PreparedStatement innerPreparedStatement;
		ResultSet innerResultSet;


		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, team.getTid());

			ResultSet resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				match = new Match(resultset.getInt("mid"), resultset.getInt("round"));

				innerPreparedStatement = connection.prepareStatement(innerSql);
				innerPreparedStatement.setInt(1, resultset.getInt("mid"));

				innerResultSet = innerPreparedStatement.executeQuery();

				while (innerResultSet.next()) {
					SQLiteTeamDao teamDao = new SQLiteTeamDao();

					team = teamDao.getTeamById(innerResultSet.getInt("tid"));

					match.addTeam(team);
					match.addPoints(team, innerResultSet.getDouble("points"));

				}

				matches.add(match);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}

	@Override
	public Match getMatchById(int mid) {
		String sql = "select * from match where mid = ?";
		String innerSql = "select * from team_match where mid = ?";

		Team team;
		Match match = null;

		PreparedStatement innerPreparedStatement;
		ResultSet innerResultSet;


		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, mid);

			ResultSet resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				match = new Match(resultset.getInt("mid"), resultset.getInt("round"));

				innerPreparedStatement = connection.prepareStatement(innerSql);
				innerPreparedStatement.setInt(1, resultset.getInt("mid"));

				innerResultSet = innerPreparedStatement.executeQuery();

				while (innerResultSet.next()) {
					SQLiteTeamDao teamDao = new SQLiteTeamDao();

					team = teamDao.getTeamById(innerResultSet.getInt("tid"));

					match.addTeam(team);
					match.addPoints(team, innerResultSet.getDouble("points"));

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return match;
	}

}
