package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.PlayerDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite-spezifischer Zugriff auf Spieler.
 * <p>
 * Die Implementierung kapselt SQL-Details, damit die Aufrufer
 * ausschließlich mit Domänenobjekten arbeiten.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.PlayerDao
 */
public class SQLitePlayerDao implements PlayerDao {

	/**
	 * Geteilte Verbindung zur Sicherstellung konsistenter Abfragen.
	 */
	private Connection connection;

	/**
	 * Initializes the DAO with a shared database connection.
	 */
	public SQLitePlayerDao() {
		try {
			this.connection = SQLiteDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @implNote Der Turnierbezug wird direkt beim Einfügen gesetzt, damit
	 *           Spieler im Turnier sofort auffindbar sind.
	 */
	@Override
	public Player addPlayer(String pname, String pemail, Tournament tournament) {
		String sql = "INSERT INTO player (pname, pemail, trid) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, pname);
			preparedStatement.setString(2, pemail);
			preparedStatement.setInt(3, tournament.getTid());

			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return getLastPlayer();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @implNote Die Turnierverknüpfung wird nachgeladen, damit die
	 *           Rückgabeobjekte sofort navigierbar sind.
	 */
	@Override
	public List<Player> getAllPlayers() {
		String sql = "SELECT * FROM player";

		List<Player> players = new ArrayList<>();

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				players.add(new Player(resultSet.getInt("pid"), resultSet.getString("pname"), resultSet.getString("pemail"), new SQLiteTournamentDao().getTournamentById(resultSet.getInt("tid"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @implNote Filterung über {@code trid} hält die Ergebnisse turnierspezifisch.
	 */
	@Override
	public List<Player> getAllPlayersFromTournament(Tournament tournament) {
		String sql = "SELECT * FROM player WHERE trid=?";

		List<Player> players = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, tournament.getTid());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				players.add(new Player(resultSet.getInt("pid"), resultSet.getString("pname"), resultSet.getString("pemail"), new SQLiteTournamentDao().getTournamentById(resultSet.getInt("tid"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @implNote Das Turnier wird nachgeladen, um eine vollständige
	 *           Spieleransicht bereitzustellen.
	 */
	@Override
	public Player getPlayerById(int pid) {
		String sql = "SELECT * FROM player WHERE pid = ?";

		Player player = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, pid);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				player = new Player(resultSet.getInt("pid"), resultSet.getString("pname"), resultSet.getString("pemail"), new SQLiteTournamentDao().getTournamentById(resultSet.getInt("tid")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return player;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean deletePlayer(Player player) {
		String sql = "DELETE FROM player WHERE mid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, player.pid());
			preparedStatement.execute();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Liefert den zuletzt persistierten Spieler zur Bestätigung der Anlage.
	 *
	 * @return zuletzt gespeicherter Spieler oder {@code null}
	 */
	private Player getLastPlayer() {
		String sql = "SELECT * FROM player ORDER BY pid DESC LIMIT 1";

		try (Statement statement = connection.createStatement(); ResultSet resultset = statement.executeQuery(sql)) {
			if (resultset.next()) {
				return getPlayerById(resultset.getInt("pid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
