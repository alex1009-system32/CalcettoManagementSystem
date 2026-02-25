package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTournamentDao implements TournamentDao {

	private Connection connection;

	public SQLiteTournamentDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addTournament(String tournament_name,
	                          int duration,
	                          int preRound,
	                          int maxTeamSize
	) {
		String sql = "INSERT INTO tournament (tournament_name, start_date, duration, pre_round, current_round, max_team_size) VALUES (?, ?, ?, ?, ?, ?);";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, tournament_name);
			preparedStatement.setString(2, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
			preparedStatement.setInt(3, duration);
			preparedStatement.setInt(4, preRound);
			preparedStatement.setDouble(5, 0);
			preparedStatement.setInt(6, maxTeamSize);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void increaseRound(Tournament tournament) {
		String sql = "UPDATE tournament SET current_round = ? WHERE tid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tournament.getCurrendRound() + 1);
			preparedStatement.setInt(2, tournament.getTid());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Tournament> getAllTournaments() {
		String sql = "select * from tournament";

		List<Tournament> tournaments = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				tournaments.add(new Tournament(
						resultSet.getInt("tid"),
						resultSet.getString("tournament_name"),
						LocalDate.parse(resultSet
								.getString("start_date")
						),
						resultSet.getInt("duration"),
						resultSet.getInt("pre_round"),
						resultSet.getInt("current_round"),
						resultSet.getInt("max_team_size")
				));
			}

			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tournaments;

	}

	@Override
	public Tournament getTournamentById(int tid) {
		String sql = "select * from tournament where tid = ?";

		Tournament tournament = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tid);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				tournament = new Tournament(
						resultSet.getInt("tid"),
						resultSet.getString("tournament_name"),
						LocalDate.parse(resultSet
								.getString("start_date")
						),
						resultSet.getInt("duration"),
						resultSet.getInt("pre_round"),
						resultSet.getInt("current_round"),
						resultSet.getInt("max_team_size")
				);
			}

			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tournament;
	}

}
