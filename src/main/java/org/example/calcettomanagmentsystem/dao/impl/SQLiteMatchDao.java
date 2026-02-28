package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite implementation of {@link MatchDao}.
 * Manages match persistence and relationships to teams and tournaments.
 */
public class SQLiteMatchDao implements MatchDao {

	private Connection connection;

	/**
	 * Initializes the DAO with a shared database connection.
	 */
	public SQLiteMatchDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Match addMatch(Tournament tournament) {
		String sql = "insert into \"match\" (round, tid) values (?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(2, tournament.getTid());
			preparedStatement.setInt(1, tournament.getCurrendRound());

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return getLastMatch();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addTeamToMatch(@NotNull Team team, Match match) {
		String sql = "INSERT INTO team_match(tid, mid) VALUES (?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, team.getTid());
			preparedStatement.setInt(2, match.getMid());

			preparedStatement.execute();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean addAddPointToTeamInMatch(Team team, Match match, double point) {
		String sql = "UPDATE team_match SET points = ? WHERE tid = ? AND mid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setDouble(1, point);
			preparedStatement.setInt(2, team.getTid());
			preparedStatement.setInt(2, match.getMid());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Match> getAllMatchesFromTournament(Tournament tournament) {
		String sql = "select * from \"match\" where tid = ?";
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Match> getAllMatchesFromTeam(Team team) {
		String sql = "SELECT * FROM \"match\" WHERE mid IN (SELECT mid FROM team_match WHERE team_match.tid = ?)";
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Match getMatchById(int mid) {
		String sql = "select * from \"match\" where mid = ?";
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


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean deleteMatch(Match match) {
		String sql = "delete from \"match\" where mid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, match.getMid());
			preparedStatement.execute();
		}  catch (SQLException e) {
			return false;
		}

		return true;
	}

	private Match getLastMatch() {
		String sql = "select * from \"match\" ORDER BY mid DESC LIMIT 1";

		Match match;
		ResultSet resultset;

		try (Statement statement= connection.createStatement()) {
			resultset = statement.executeQuery(sql);
			while (resultset.next()) {
				return getMatchById(
						resultset.getInt("mid")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
