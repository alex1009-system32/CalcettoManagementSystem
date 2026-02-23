package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.PlayerDao;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLitePlayerDao implements PlayerDao {

	private Connection connection;

	public SQLitePlayerDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addPlayer(String pname, String pemail, Tournament tournament) {
		String sql = "insert into player (pname, pemail, trid) values (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, pname);
			preparedStatement.setString(2, pemail);
			preparedStatement.setInt(3, tournament.getTid());

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Player> getAllPlayers() {
		String sql = "select * from player";

		List<Player> players = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				players.add(new Player(
						resultSet.getInt("pid"),
						resultSet.getString("pname"),
						resultSet.getString("pemail"),
						new SQLiteTournamentDao().getTournamentById(
								resultSet.getInt("tid")
						)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;
	}

	@Override
	public List<Player> getAllPlayersFromTournament(Tournament tournament) {
		String sql = "select * from player WHERE trid=?";

		List<Player> players = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tournament.getTid());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				players.add(new Player(
						resultSet.getInt("pid"),
						resultSet.getString("pname"),
						resultSet.getString("pemail"),
						new SQLiteTournamentDao().getTournamentById(
								resultSet.getInt("tid")
						)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;
	}

	@Override
	public Player getPlayerById(int pid) {
		String sql = "select * from player where pid = ?";

		Player player = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, pid);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				player = new Player(
						resultSet.getInt("pid"),
						resultSet.getString("pname"),
						resultSet.getString("pemail"),
						new SQLiteTournamentDao().getTournamentById(
								resultSet.getInt("tid")
						)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return player;

	}
}
