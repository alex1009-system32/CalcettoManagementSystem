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

/**
 * SQLite-Implementierung für Turnier-Persistenz.
 * <p>
 * Ziel ist eine stabile CRUD-Schnittstelle für Turnierdaten,
 * damit UI und Logik keine SQL-Details kennen müssen.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.TournamentDao
 */

public class SQLiteTournamentDao implements TournamentDao {

	/**
	 * Geteilte Verbindung für konsistente Lese- und Schreibzugriffe.
	 */
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
	 * Persistiert ein Turnier mit allen Startparametern.
	 *
	 * @param tournament_name Anzeigename, der in der UI verwendet wird
	 * @param duration Dauer in Tagen für die Turnierplanung
	 * @param preRound Anzahl der Vorrunden für die Match-Logik
	 * @param maxTeamSize maximale Teamgröße für Team-Matching
	 * @return neu erstelltes Turnier
	 */
	@Override
	public Tournament addTournament(String tournament_name, int duration, int preRound, int maxTeamSize) {
		String sql = "INSERT INTO tournament (tournament_name, start_date, duration, pre_round, current_round, max_team_size) VALUES (?, ?, ?, ?, ?, ?);";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, tournament_name);
			preparedStatement.setString(2, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
			preparedStatement.setInt(3, duration);
			preparedStatement.setInt(4, preRound);
			preparedStatement.setInt(5, 0);
			preparedStatement.setInt(6, maxTeamSize);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return getLastTournament();
	}

	/**
	 * Aktualisiert den Fortschritt, damit Folgerunden konsistent bleiben.
	 *
	 * @param tournament Turnier, dessen Runde erhöht wird
	 * @return {@code true} bei erfolgreicher Aktualisierung
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
	 * Lädt alle Turniere für Auswahl und Übersicht.
	 *
	 * @return Liste der Turniere
	 */
	@Override
	public List<Tournament> getAllTournaments() {
		String sql = "SELECT * FROM tournament";

		List<Tournament> tournaments = new ArrayList<>();

		try (Statement statement = connection.createStatement();){
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				tournaments.add(new Tournament(resultSet.getInt("tid"), resultSet.getString("tournament_name"), LocalDate.parse(resultSet.getString("start_date")), resultSet.getInt("duration"), resultSet.getInt("pre_round"), resultSet.getInt("current_round"), resultSet.getInt("max_team_size")));
			}

			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tournaments;

	}

	/**
	 * Liefert ein Turnier anhand seiner ID für Detailansichten.
	 *
	 * @param tid Turnier-ID
	 * @return Turnier oder {@code null}, wenn nicht vorhanden
	 */
	@Override
	public Tournament getTournamentById(int tid) {
		String sql = "SELECT * FROM tournament WHERE tid = ?";

		Tournament tournament = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tid);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				tournament = new Tournament(resultSet.getInt("tid"), resultSet.getString("tournament_name"), LocalDate.parse(resultSet.getString("start_date")), resultSet.getInt("duration"), resultSet.getInt("pre_round"), resultSet.getInt("current_round"), resultSet.getInt("max_team_size"));
			}

			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tournament;
	}

	@Override
	public boolean deleteTournament(Tournament tournament) {
		String sql = "DELETE FROM tounament WHERE tid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tournament.getTid());
			preparedStatement.execute();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Liefert das zuletzt persistierte Turnier für Folgeoperationen.
	 *
	 * @return zuletzt gespeichertes Turnier oder {@code null}
	 */
	private Tournament getLastTournament() {
		String sql = "SELECT * FROM tournament ORDER BY tid DESC LIMIT 1";

		try (Statement statement = connection.createStatement(); ResultSet resultset = statement.executeQuery(sql)) {
			if (resultset.next()) {
				return getTournamentById(resultset.getInt("tid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}


}
