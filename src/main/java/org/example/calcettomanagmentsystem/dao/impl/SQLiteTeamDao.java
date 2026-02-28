package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite implementation of {@link org.example.calcettomanagmentsystem.dao.TeamDao}.
 * <p>
 * Provides persistence operations for Team entities and their player memberships
 * using a shared {@link java.sql.Connection} from {@link org.example.calcettomanagmentsystem.connection.SQLiteDB}.
 * </p>
 * <p>
 * Responsibilities include creating teams, assigning players to teams, and loading teams
 * by tournament, id, or unique name.
 * </p>
 */

public class SQLiteTeamDao implements TeamDao {

	private Connection connection;

	/**
	 * Initializes the DAO with a shared database connection.
	 */
	public SQLiteTeamDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new team row in the database.
	 *
	 * @param teamname team display name
	 * @return the persisted team
	 */
	@Override
	public Team addTeam(String teamname) {
		String sql = "INSERT INTO team (team_name) VALUES (?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, teamname);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return getLastTeam();
	}

	/**
	 * Assigns a player to a team and persists the relationship.
	 *
	 * @param player player to add
	 * @param team   team to receive the player
	 * @return true if the update succeeded
	 */
	@Override
	public boolean addPlayerToTeam(Player player, Team team) {
		String sql = "UPDATE player SET player.tid = ? WHERE player.pid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, team.getTid());
			preparedStatement.setInt(2, player.pid());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			return false;
		}

		team.addPlayer(player);
		return true;
	}

	/**
	 * Loads all teams that have players participating in the given tournament.
	 *
	 * @param tournament tournament filter
	 * @return list of teams enriched with their players
	 */
	@Override
	public List<Team> getAllTeamsFromTournament(Tournament tournament) {
		String sql = "SELECT * FROM team WHERE tid IN(SELECT tid FROM player WHERE trid = ?)";
		String innerSql = "SELECT * FROM player WHERE tid = ?";

		List<Team> teams = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql); PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql)) {
			preparedStatement.setInt(1, tournament.getTid());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Team team = new Team(resultSet.getInt("tid"), resultSet.getString("team_name"));
					innerPreparedStatement.setInt(1, resultSet.getInt("tid"));

					try (ResultSet innerResultSet = innerPreparedStatement.executeQuery()) {
						while (innerResultSet.next()) {
							team.addPlayer(new SQLitePlayerDao().getPlayerById(innerResultSet.getInt("pid")));
						}
					}

					teams.add(team);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return teams;

	}

	/**
	 * Finds a team by id and loads its players.
	 *
	 * @param tid team id
	 * @return team or null if not found
	 */
	@Override
	public Team getTeamById(int tid) {
		String sql = "SELECT * FROM team WHERE tid = ?";
		String innerSql = "SELECT * FROM player WHERE tid = ?";

		Team team = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql); PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql)) {
			preparedStatement.setInt(1, tid);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					team = new Team(resultSet.getInt("tid"), resultSet.getString("team_name"));
					innerPreparedStatement.setInt(1, resultSet.getInt("tid"));

					try (ResultSet innerResultSet = innerPreparedStatement.executeQuery()) {
						while (innerResultSet.next()) {
							team.addPlayer(new SQLitePlayerDao().getPlayerById(innerResultSet.getInt("pid")));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return team;
	}

	/**
	 * Finds a team by its unique name and loads its players.
	 *
	 * @param teamName unique team name
	 * @return team or null if not found
	 */
	@Override
	public Team getTeamByName(String teamName) {
		String sql = "SELECT * FROM team WHERE team_name = ?";
		String innerSql = "SELECT * FROM player WHERE tid = ?";

		Team team = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql); PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql)) {
			preparedStatement.setString(1, teamName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					team = new Team(resultSet.getInt("tid"), resultSet.getString("team_name"));
					innerPreparedStatement.setInt(1, resultSet.getInt("tid"));

					try (ResultSet innerResultSet = innerPreparedStatement.executeQuery()) {
						while (innerResultSet.next()) {
							team.addPlayer(new SQLitePlayerDao().getPlayerById(innerResultSet.getInt("pid")));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return team;
	}

	@Override
	public boolean deleteTeam(Team team) {
		String sql = "DELETE FROM team WHERE tid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, team.getTid());
			preparedStatement.execute();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Helper that retrieves the most recently inserted team.
	 *
	 * @return last persisted team or null if none
	 */
	private Team getLastTeam() {
		String sql = "SELECT * FROM team ORDER BY tid DESC LIMIT 1";

		ResultSet resultset;

		try (Statement statement = connection.createStatement()) {
			resultset = statement.executeQuery(sql);
			while (resultset.next()) {
				return getTeamById(resultset.getInt("tid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
