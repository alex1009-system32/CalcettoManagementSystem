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
 *
 * <p>
 *     this class is responsible for inserting into and select team objects.
 *     and them form the databank.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0
 *
 */

public class SQLiteTeamDao implements TeamDao {

	private Connection connection;

	public SQLiteTeamDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * <p>
	 *     Inserts into the Databank a new team.
	 * </p>
	 *
	 * @param teamname need to create a new team.
	 */
	@Override
	public void addTeam(String teamname) {
		String sql = "INSERT INTO team (team_name) VALUES (?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, teamname);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 *
	 * <p>
	 *     This method adds a new player object into a team object.
	 * </p>
	 * <p>
	 *     Simultaneously it Inserts Into the Databank the relationship between player and team.
	 * </p>
	 *
	 * @param player needs a player object.
	 * @param team needs a team object.
	 */

	@Override
	public void addPlayerToTeam(Player player, Team team) {
		String sql = "UPDATE player SET player.tid = ? WHERE player.pid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, team.getTid());
			preparedStatement.setInt(2, player.pid());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		team.addPlayer(player);
	}

	/**
	 *
	 * <p>
	 *     This method is responsible for returning a List of Teams.
	 *     These team objects stand in relationship with the tournament object.
	 * </p>
	 *
	 * @param tournament is necessary for getting the right Team objects.
	 * @return It returns a List of objects.
	 */
	@Override
	public List<Team> getAllTeamsFromTournament(Tournament tournament) {
		String sql = "SELECT * FROM team WHERE tid IN(SELECT tid FROM player WHERE trid = ?)";
		String innerSql = "select * from player where tid = ?";

		List<Team> teams = new ArrayList<>();

		PreparedStatement innerPreparedStatement;
		ResultSet innerResultSet;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tournament.getTid());

			ResultSet resultSet = preparedStatement.executeQuery();
			innerPreparedStatement = connection.prepareStatement(innerSql);

			while (resultSet.next()) {
				Team team = new Team(resultSet.getInt("tid"), resultSet.getString("team_name"));
				innerPreparedStatement.setInt(1, resultSet.getInt("tid"));

				innerResultSet = innerPreparedStatement.executeQuery();

				while (innerResultSet.next()) {
					team.addPlayer(
							new SQLitePlayerDao().getPlayerById(
									innerResultSet.getInt("pid")
							)
					);
				}

				teams.add(team);

			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return teams;

	}

	/**
	 *
	 * <p>
	 *     This method returns a Team object with the right id.
	 * </p>
	 *
	 * @param tid is necessary for filtering
	 * @return It returns one Team object
	 */
	@Override
	public Team getTeamById(int tid) {
		String sql = "select * from team where tid = ?";
		String innerSql = "select * from player where tid = ?";

		PreparedStatement innerPreparedStatement;
		ResultSet innerResultSet;

		Team team = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tid);

			ResultSet resultSet = preparedStatement.executeQuery();
			innerPreparedStatement = connection.prepareStatement(innerSql);

			while (resultSet.next()) {
				team = new Team(resultSet.getInt("tid"), resultSet.getString("team_name"));
				innerPreparedStatement.setInt(1, resultSet.getInt("tid"));

				innerResultSet = innerPreparedStatement.executeQuery();

				while (innerResultSet.next()) {
					team.addPlayer(
							new SQLitePlayerDao().getPlayerById(
									innerResultSet.getInt("pid")
							)
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return team;
	}

	/**
	 *
	 * <p>
	 *     This method returns a Team object with the right teamName.
	 * </p>
	 *
	 * @param teamName is necessary for filtering
	 * @return It returns one Team object.
	 */
	@Override
	public Team getTeamByName(String teamName) {
		String sql = "select * from team where team_name = ?";
		String innerSql = "select * from player where tid = ?";

		PreparedStatement innerPreparedStatement;
		ResultSet innerResultSet;

		Team team = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, teamName);

			ResultSet resultSet = preparedStatement.executeQuery();
			innerPreparedStatement = connection.prepareStatement(innerSql);

			while (resultSet.next()) {
				team = new Team(resultSet.getInt("tid"), resultSet.getString("team_name"));
				innerPreparedStatement.setInt(1, resultSet.getInt("tid"));

				innerResultSet = innerPreparedStatement.executeQuery();

				while (innerResultSet.next()) {
					team.addPlayer(
							new SQLitePlayerDao().getPlayerById(
									innerResultSet.getInt("pid")
							)
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return team;
	}
}
