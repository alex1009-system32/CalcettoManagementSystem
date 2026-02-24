package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTeamDao implements TeamDao {

	private Connection connection;

	public SQLiteTeamDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
}
