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
 * SQLite-spezifische Implementierung für Match-Persistenz.
 * <p>
 * Die Klasse kapselt die SQL-Details, damit die Domänenlogik
 * keine Datenbankkenntnisse benötigt.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.MatchDao
 */
public class SQLiteMatchDao implements MatchDao {
	private Connection connection;
	/**
	 * Geteilte Verbindung, um konsistente Transaktionen zu ermöglichen.
	 */


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
	 *
	 * @implNote Das Match wird mit der aktuellen Turnierrunde angelegt, damit
	 *           Folgeabfragen über {@code round} konsistent bleiben.
	 */
	@Override
	public Match addMatch(Tournament tournament) {
		String sql = "INSERT INTO \"match\" (round, tid) VALUES (?, ?)";

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

	/**
	 * {@inheritDoc}
	 *
	 * @implNote Punkte werden separat aktualisiert, damit Ergebnisänderungen
	 *           nicht die Match-Zuordnung beeinflussen.
	 */
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
	 *
	 * @implNote Für jedes Match werden Team-Informationen nachgeladen, um
	 *           vollständige Match-Objekte zu liefern.
	 */
	@Override
	public List<Match> getAllMatchesFromTournament(Tournament tournament) {
		String sql = "SELECT * FROM \"match\" WHERE tid = ?";
		String innerSql = "SELECT * FROM team_match WHERE mid = ?";

		Team team;
		Match match;
		List<Match> matches = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
		     PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql)) {
			preparedStatement.setInt(1, tournament.getTid());

			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					match = new Match(resultset.getInt("mid"), resultset.getInt("round"));

					innerPreparedStatement.setInt(1, resultset.getInt("mid"));

					try (ResultSet innerResultSet = innerPreparedStatement.executeQuery()) {
						while (innerResultSet.next()) {
							SQLiteTeamDao teamDao = new SQLiteTeamDao();

							team = teamDao.getTeamById(innerResultSet.getInt("tid"));

							match.addTeam(team);
							match.addPoints(team, innerResultSet.getDouble("points"));
						}
					}

					matches.add(match);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @implNote Der Rundenfilter reduziert bewusst die Datenmenge für die UI.
	 */
	@Override
	public List<Match> getAllMatchesFromTournamentInRound(Tournament tournament, int round) {
		String sql = "SELECT * FROM \"match\" WHERE tid = ? AND round = ?";
		String innerSql = "SELECT * FROM team_match WHERE mid = ?";

		Team team;
		Match match;
		List<Match> matches = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
		     PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql)) {
			preparedStatement.setInt(1, tournament.getTid());
			preparedStatement.setInt(2, round);

			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					match = new Match(resultset.getInt("mid"), resultset.getInt("round"));

					innerPreparedStatement.setInt(1, resultset.getInt("mid"));

					try (ResultSet innerResultSet = innerPreparedStatement.executeQuery()) {
						while (innerResultSet.next()) {
							SQLiteTeamDao teamDao = new SQLiteTeamDao();

							team = teamDao.getTeamById(innerResultSet.getInt("tid"));

							match.addTeam(team);
							match.addPoints(team, innerResultSet.getDouble("points"));
						}
					}

					matches.add(match);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @implNote Die Relation wird über {@code team_match} aufgelöst, um
	 *           auch Ergebnisse pro Team laden zu können.
	 */
	@Override
	public List<Match> getAllMatchesFromTeam(Team team) {
		String sql = "SELECT * FROM \"match\" WHERE mid IN (SELECT mid FROM team_match WHERE team_match.tid = ?)";
		String innerSql = "SELECT * FROM team_match WHERE mid = ?";

		Match match;
		List<Match> matches = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
		     PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql)) {
			preparedStatement.setInt(1, team.getTid());

			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					match = new Match(resultset.getInt("mid"), resultset.getInt("round"));

					innerPreparedStatement.setInt(1, resultset.getInt("mid"));

					try (ResultSet innerResultSet = innerPreparedStatement.executeQuery()) {
						while (innerResultSet.next()) {
							SQLiteTeamDao teamDao = new SQLiteTeamDao();

							Team teamObj = teamDao.getTeamById(innerResultSet.getInt("tid"));

							match.addTeam(teamObj);
							match.addPoints(teamObj, innerResultSet.getDouble("points"));
						}
					}

					matches.add(match);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @implNote Der Match-Lookup lädt zusätzlich die Team-Zuordnung und Punkte,
	 *           damit die Match-Entität vollständig nutzbar ist.
	 */
	@Override
	public Match getMatchById(int mid) {
		String sql = "SELECT * FROM \"match\" WHERE mid = ?";
		String innerSql = "SELECT * FROM team_match WHERE mid = ?";

		Team team;
		Match match = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
		     PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql)) {
			preparedStatement.setInt(1, mid);

			try (ResultSet resultset = preparedStatement.executeQuery()) {
				while (resultset.next()) {
					match = new Match(resultset.getInt("mid"), resultset.getInt("round"));

					innerPreparedStatement.setInt(1, resultset.getInt("mid"));

					try (ResultSet innerResultSet = innerPreparedStatement.executeQuery()) {
						while (innerResultSet.next()) {
							SQLiteTeamDao teamDao = new SQLiteTeamDao();

							team = teamDao.getTeamById(innerResultSet.getInt("tid"));

							match.addTeam(team);
							match.addPoints(team, innerResultSet.getDouble("points"));
						}
					}
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
		String sql = "DELETE FROM \"match\" WHERE mid = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, match.getMid());
			preparedStatement.execute();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Liefert das zuletzt angelegte Match für Folgeoperationen.
	 *
	 * @return zuletzt gespeichertes Match oder {@code null}
	 */
	private Match getLastMatch() {
		String sql = "SELECT * FROM \"match\" ORDER BY mid DESC LIMIT 1";

		Match match;
		ResultSet resultset;

		try (Statement statement = connection.createStatement()) {
			resultset = statement.executeQuery(sql);
			while (resultset.next()) {
				return getMatchById(resultset.getInt("mid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
