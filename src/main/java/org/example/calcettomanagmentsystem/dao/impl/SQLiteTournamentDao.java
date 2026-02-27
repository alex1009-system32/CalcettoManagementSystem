package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite implementation of {@link org.example.calcettomanagmentsystem.dao.TournamentDao}.
 * <p>
 * Provides persistence operations for Tournament entities using a shared
 * {@link java.sql.Connection} from {@link org.example.calcettomanagmentsystem.connection.SQLiteDB}.
 * </p>
 * <p>
 * Responsibilities include creating tournaments, increasing the current round,
 * listing and retrieving tournaments, and deleting tournaments.
 * </p>
 */

public class SQLiteTournamentDao implements TournamentDao {

	private Connection connection;

	/**
	 * Initializes the DAO with a shared database connection.
	 */
	public SQLiteTournamentDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Persists a new tournament with the provided attributes.
	 *
	 * @param tournament_name tournament name
	 * @param duration duration in days
	 * @param preRound number of preliminary rounds
	 * @param maxTeamSize max players per team
	 * @return newly created tournament
	 */
	@Override
	public Tournament addTournament(String tournament_name,
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

		return getLastTournament();
	}

	/**
	 * Increments the current round of the given tournament in the database.
	 *
	 * @param tournament tournament to update
	 * @return true if the update succeeded
	 */
	@Override
	public boolean increaseRound(Tournament tournament) {
		String sql = "UPDATE tournament SET current_round = ? WHERE tid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tournament.getCurrendRound() + 1);
			preparedStatement.setInt(2, tournament.getTid());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Loads all tournaments from the database.
	 *
	 * @return list of tournaments
	 */
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

	/**
	 * Retrieves a single tournament by id.
	 *
	 * @param tid tournament id
	 * @return tournament or null if not found
	 */

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

	@Override
	public boolean deleteTournament(Tournament tournament) {
		String sql = "delete from tounament where tid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tournament.getTid());
			preparedStatement.execute();
		}  catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Helper that retrieves the last persisted tournament.
	 *
	 * @return last created tournament or null if none
	 */
	private Tournament getLastTournament() {
		String sql = "select * from tournament ORDER BY mid DESC LIMIT 1";
		ResultSet resultset;

		try (Statement statement= connection.createStatement()) {
			resultset = statement.executeQuery(sql);
			while (resultset.next()) {
				return getTournamentById(
						resultset.getInt("tid")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}


}
