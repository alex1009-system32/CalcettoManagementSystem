package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite-Implementierung für Team-Persistenz und Spielerzuordnung.
 * <p>
 * Die Klasse kapselt die SQL-Details, damit die Team-Logik
 * unabhängig von der Speichertechnik bleibt.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.dao.TeamDao
 */

public class SQLiteTeamDao implements TeamDao {

    /**
     * Geteilte Verbindung für konsistente Lese- und Schreiboperationen.
     */
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
     * Erstellt ein Team und liefert es für direkte Weiterverarbeitung zurück.
     *
     * @param teamname gewünschter Anzeigename des Teams
     * @return persistiertes Team mit ID
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
     * Persistiert die Teamzuordnung eines Spielers, um Teamroster stabil abzubilden.
     *
     * @param player Spieler, der dem Team zugeordnet wird
     * @param team   Zielteam für die Zuordnung
     * @return {@code true} bei erfolgreicher Aktualisierung
     */
    @Override
    public boolean addPlayerToTeam(@NotNull Player player, @NotNull Team team) {
        String sql = "UPDATE player SET tid = ? WHERE pid = ?";

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
     * Lädt Teams eines Turniers inklusive Spieler, um Bracket-Logik zu unterstützen.
     *
     * @param tournament Turnierfilter
     * @return Teams mit vollständig geladenen Spielern
     */
    @Override
    public List<Team> getAllTeamsFromTournament(@NotNull Tournament tournament) {
        String sql = "SELECT * FROM team WHERE tid IN(SELECT tid FROM player WHERE trid = ?)";
        String innerSql = "SELECT * FROM player WHERE tid = ?";

        List<Team> teams = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql); PreparedStatement innerPreparedStatement = connection.prepareStatement(
                innerSql)) {
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

    @Override
    public List<Team> getAllTeams() {
        String sql = "SELECT * FROM team)";
        String innerSql = "SELECT * FROM player WHERE tid = ?";

        List<Team> teams = new ArrayList<>();

        try (Statement statement = connection.createStatement(); PreparedStatement innerPreparedStatement = connection.prepareStatement(
                innerSql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
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
     * Lädt ein Team mit Spielern für Detailansichten und Auswertungen.
     *
     * @param tid Team-ID
     * @return Team oder {@code null}, wenn nicht vorhanden
     */
    @Override
    public Team getTeamById(int tid) {
        String sql = "SELECT * FROM team WHERE tid = ?";
        String innerSql = "SELECT * FROM player WHERE tid = ?";

        Team team = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql); PreparedStatement innerPreparedStatement = connection.prepareStatement(
                innerSql)) {
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
     * Lädt ein Team anhand des Namens, um Eingaben aus der UI zu unterstützen.
     *
     * @param teamName eindeutiger Teamname
     * @return Team oder {@code null}, wenn nicht vorhanden
     */
    @Override
    public Team getTeamByName(String teamName) {
        String sql = "SELECT * FROM team WHERE team_name = ?";
        String innerSql = "SELECT * FROM player WHERE tid = ?";

        Team team = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql); PreparedStatement innerPreparedStatement = connection.prepareStatement(
                innerSql)) {
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
    public boolean deleteTeam(@NotNull Team team) {
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
     * Liefert das zuletzt angelegte Team für Folgeoperationen.
     *
     * @return zuletzt persistiertes Team oder {@code null}
     */
    private @Nullable Team getLastTeam() {
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
