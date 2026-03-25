package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SQLiteTeamDao implements TeamDao {
    DataBaseSource dataBaseSource;

    public SQLiteTeamDao(DataBaseSource dataBaseSource) {
        this.dataBaseSource = dataBaseSource;
    }

    private void mapResultSetToTeam(ResultSet rs,
                                    Map<Integer, Team> teamMap,
                                    Map<Integer, Tournament> tournamentCache) throws SQLException {
        int teamId = rs.getInt("tid");
        Team team = teamMap.computeIfAbsent(teamId, id -> {
            try {
                return new Team(id, rs.getString("team_name"), new ArrayList<>());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        int tourneyId = rs.getInt("trid");
        Tournament tournament = null;

        if (!rs.wasNull()) {
            tournament = tournamentCache.computeIfAbsent(tourneyId, id -> {
                try {
                    return new Tournament(id,
                                          rs.getString("tournament_name"),
                                          LocalDate.parse(rs.getString("start_date")),
                                          rs.getInt("duration"),
                                          rs.getInt("pre_round"),
                                          rs.getInt("current_round"),
                                          rs.getInt("max_team_size"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        int playerId = rs.getInt("pid");
        Player player;

        if (!rs.wasNull()) {
            player = new Player(playerId, rs.getString("pname"), rs.getString("pemail"), tournament);
            if (!team.players().contains(player)) {
                team.players().add(player);
            }
        }
    }

    @Override
    public Optional<Team> save(Team obj) {
        String sql = "INSERT INTO team (team_name) VALUES (?)";

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setString(1, obj.name());

            int affected = preparedStatement.executeUpdate();
            if (affected == 0) throw new DataAccessException("Update failed");

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    return Optional.ofNullable(findById(resultSet.getInt(1)))
                                   .orElseThrow(() -> new DataAccessException("Team not found"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while Saving Team", e);
        }

        return Optional.empty();
    }

    @Override
    public Team addPlayer(Team team, Player player) {
        String sql = "Update player set tid = ? where pid = ?";

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, team.id());
            preparedStatement.setInt(2, player.id());

            int affected = preparedStatement.executeUpdate();
            if (affected == 0) throw new DataAccessException("Update failed");

            if (!team.players().contains(player)) {
                team.players().add(player);
            }

            return team;

        } catch (SQLException e) {
            throw new DataAccessException("Error while Updating Player", e);
        }
    }

    @Override
    public List<Team> findAll() {
        String sql = """
                SELECT t.tid, t.team_name,
                           p.pid, p.pname, p.pemail,
                           tr.tid AS trid, tr.tournament_name, tr.start_date, tr.duration, tr.pre_round, tr.current_round, tr.max_team_size
                    FROM team t
                    LEFT JOIN player p ON t.tid = p.tid
                    LEFT JOIN tournament tr ON p.trid = tr.tid
                    ORDER BY t.tid
                """;

        Map<Integer, Team> teamMap = new LinkedHashMap<>();
        Map<Integer, Tournament> tournamentCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                mapResultSetToTeam(resultSet, teamMap, tournamentCache);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while loading Teams out of Database", e);
        }

        return new ArrayList<>(teamMap.values());
    }

    @Override
    public List<Team> findByTournament(Tournament tournament) {
        String sql = """
                SELECT t.tid, t.team_name,
                                           p.pid, p.pname, p.pemail,
                                           tr.tid AS trid, tr.tournament_name, tr.start_date, tr.duration, tr.pre_round, tr.current_round, tr.max_team_size
                                    FROM team t
                                    LEFT JOIN player p ON t.tid = p.tid
                                    LEFT JOIN tournament tr ON p.trid = tr.tid
                                    WHERE tr.tid = ?
                                    ORDER BY t.tid
                """;

        Map<Integer, Team> teamMap = new LinkedHashMap<>();
        Map<Integer, Tournament> tournamentCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, tournament.id());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    mapResultSetToTeam(resultSet, teamMap, tournamentCache);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while loading Teams out of Database", e);
        }

        return new ArrayList<>(teamMap.values());
    }

    @Override
    public boolean delete(Team obj) {
        String sql = "DELETE FROM team WHERE tid = ?";

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            int affected = preparedStatement.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error while Deleting Team", e);
        }
    }

    @Override
    public Optional<Team> findById(int id) {
        String sql = """
                SELECT t.tid, t.team_name,
                           p.pid, p.pname, p.pemail,
                           tr.tid AS trid, tr.tournament_name, tr.start_date, tr.duration, tr.pre_round, tr.current_round, tr.max_team_size
                    FROM team t
                    LEFT JOIN player p ON t.tid = p.tid
                    LEFT JOIN tournament tr ON p.trid = tr.tid
                    WHERE t.tid = ?
                    ORDER BY t.tid
                """;

        Map<Integer, Team> teamMap = new LinkedHashMap<>();
        Map<Integer, Tournament> tournamentCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapResultSetToTeam(resultSet, teamMap, tournamentCache);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while loading Team with ID " + id, e);
        }

        return Optional.ofNullable(teamMap.get(id));
    }
}
