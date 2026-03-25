package org.example.calcettomanagmentsystem.core.dao.impl;

import org.example.calcettomanagmentsystem.core.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.core.dao.MatchDao;
import org.example.calcettomanagmentsystem.shared.exceptions.DataAccessException;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * SQLite implementation of the {@link MatchDao} interface.
 * <p>
 * This class provides methods to interact with a SQLite database to manage
 * match records, including score assignments and tournament filtering.
 * </p>
 *
 * @author Senior Developer
 */
public class SQLiteMatchDao implements MatchDao {
    /** The source providing database connections. */
    DataBaseSource dataBaseSource;

    /**
     * Constructs a new SQLiteMatchDao with the specified data source.
     *
     * @param dataBaseSource The database connection source.
     */
    public SQLiteMatchDao(DataBaseSource dataBaseSource) {
        this.dataBaseSource = dataBaseSource;
    }

    /**
     * Maps a row from a {@link ResultSet} to a {@link Match} object, handling tournament and team caching.
     *
     * @param rs The result set containing match data.
     * @param matchMap A map of existing matches for round grouping.
     * @param tournamentCache A cache for {@link Tournament} objects to avoid redundant mapping.
     * @param teamCache A cache for {@link Team} objects.
     * @throws SQLException If database access fails.
     */
    private void mapResultSetToMatch(ResultSet rs,
                                     Map<Integer, Match> matchMap,
                                     Map<Integer, Tournament> tournamentCache,
                                     Map<Integer, Team> teamCache) throws SQLException {

        int matchId = rs.getInt("mid");

        Match match = matchMap.computeIfAbsent(matchId, id -> {
            try {
                int trid = rs.getInt("trid");
                Tournament tournament = tournamentCache.computeIfAbsent(trid, tId -> {
                    try {
                        return new Tournament(tId, rs.getString("tournament_name"), LocalDate.parse(rs.getString("start_date")), rs.getInt("duration"), rs.getInt("pre_round"), rs.getInt("current_round"), rs.getInt("max_team_size"));
                    } catch (SQLException e) {
                        throw new RuntimeException("Mapping error for tournament ID " + tId, e);
                    }
                });

                return new Match(id, rs.getInt("round"), tournament);
            } catch (SQLException e) {
                throw new RuntimeException("Mapping error for match ID " + id, e);
            }
        });

        int teamId = rs.getInt("team_id");
        if (teamId > 0) {
            Team team = teamCache.computeIfAbsent(teamId, tId -> {
                try {
                    return new Team(tId, rs.getString("team_name"));
                } catch (SQLException e) {
                    throw new RuntimeException("Mapping error for team ID " + tId, e);
                }
            });
            match.teamResults().put(team, rs.getDouble("points"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Match> save(Match obj) {
        String sql = "INSERT INTO \"match\" (round, tid) VALUES (?, ?)";

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, obj.round());
            preparedStatement.setInt(2, obj.tournament().id());

            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    return Optional.ofNullable(new Match(resultSet.getInt(1), obj.round(), obj.tournament()));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert match into database.", e);
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match registerTeam(Team team, Match match) {
        String sql = "INSERT INTO team_match(tid, mid) VALUES (?, ?)";

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, team.id());
            preparedStatement.setInt(2, match.id());

            int affected = preparedStatement.executeUpdate();
            if (affected == 0) throw new DataAccessException("Match association could not be saved.");

            if (!match.teamResults().containsKey(team)) {
                match.teamResults().put(team, -1.0);
            }

            return match;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert team-match association into database.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match assignPoints(Team team, double point, Match match) {
        String sql = "UPDATE team_match SET points = ? WHERE tid = ? AND mid = ?";

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, point);
            preparedStatement.setInt(2, team.id());
            preparedStatement.setInt(3, match.id());

            int affected = preparedStatement.executeUpdate();
            if (affected == 0) throw new DataAccessException("Point assignment could not be updated.");

            match.teamResults().put(team, point);
            return match;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update points in database.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> findMatchesByTournament(@NotNull Tournament tournament) {
        String sql = """
                SELECT m.mid, m.round, 
                       t.tid AS team_id, t.team_name,
                       tm.points
                FROM "match" m
                LEFT JOIN team_match tm ON m.mid = tm.mid
                LEFT JOIN team t ON tm.tid = t.tid
                WHERE m.tid = ?
                ORDER BY m.round, m.mid
                """;

        Map<Integer, Match> matchMap = new LinkedHashMap<>();
        Map<Integer, Team> teamCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setInt(1, tournament.id());

            try (ResultSet rs = prepareStatement.executeQuery()) {
                while (rs.next()) {
                    int matchId = rs.getInt("mid");

                    Match match = matchMap.computeIfAbsent(matchId, id -> {
                        try {
                            return new Match(id, rs.getInt("round"), tournament);
                        } catch (SQLException e) {
                            throw new DataAccessException("Failed to map match from result set.", e);
                        }
                    });

                    int teamId = rs.getInt("team_id");
                    if (teamId > 0) {
                        Team team = teamCache.computeIfAbsent(teamId, tId -> {
                            try {
                                return new Team(tId, rs.getString("team_name"));
                            } catch (SQLException e) {
                                throw new RuntimeException("Failed to map team from result set.", e);
                            }
                        });

                        match.teamResults().put(team, rs.getDouble("points"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve tournament matches from database.", e);
        }

        return new ArrayList<>(matchMap.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> findMatchesByTournament(@NotNull Tournament tournament, int round) {
        String sql = """
                SELECT m.mid, m.round, t.tid AS team_id, t.team_name, tm.points
                                FROM "match" m
                                LEFT JOIN team_match tm ON m.mid = tm.mid
                                LEFT JOIN team t ON tm.tid = t.tid
                                WHERE m.tid = ? and m.round = ?
                                ORDER BY m.round, m.mid
                """;

        Map<Integer, Match> matchMap = new LinkedHashMap<>();
        Map<Integer, Team> teamCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setInt(1, tournament.id());
            prepareStatement.setInt(2, round);

            try (ResultSet rs = prepareStatement.executeQuery()) {
                while (rs.next()) {
                    int matchId = rs.getInt("mid");

                    Match match = matchMap.computeIfAbsent(matchId, id -> {
                        try {
                            return new Match(id, rs.getInt("round"), tournament);
                        } catch (SQLException e) {
                            throw new DataAccessException("Failed to map match from result set.", e);
                        }
                    });

                    int teamId = rs.getInt("team_id");
                    if (teamId > 0) {
                        Team team = teamCache.computeIfAbsent(teamId, tId -> {
                            try {
                                return new Team(tId, rs.getString("team_name"));
                            } catch (SQLException e) {
                                throw new RuntimeException("Failed to map team from result set.", e);
                            }
                        });

                        match.teamResults().put(team, rs.getDouble("points"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve round matches from database.", e);
        }

        return new ArrayList<>(matchMap.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> findAll() {
        String sql = """
                SELECT m.mid, m.round, 
                       tr.tid AS trid, tr.tournament_name, tr.start_date, tr.duration, tr.pre_round, tr.current_round, tr.max_team_size,
                       t.tid AS team_id, t.team_name,
                       tm.points
                FROM "match" m
                JOIN tournament tr ON m.tid = tr.tid
                LEFT JOIN team_match tm ON m.mid = tm.mid
                LEFT JOIN team t ON tm.tid = t.tid
                ORDER BY m.mid
                """;

        Map<Integer, Match> matchMap = new LinkedHashMap<>();
        Map<Integer, Tournament> tournamentCache = new HashMap<>();
        Map<Integer, Team> teamCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql);
             ResultSet rs = prepareStatement.executeQuery()) {

            while (rs.next()) {
                mapResultSetToMatch(rs, matchMap, tournamentCache, teamCache);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve all matches from database.", e);
        }

        return new ArrayList<>(matchMap.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Match obj) {
        String sql = "DELETE FROM \"match\" WHERE mid = ?";

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, obj.id());

            int affected = preparedStatement.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete match record from database.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Match> findById(int id) {
        String sql = """
                SELECT m.mid, m.round, 
                       tr.tid AS trid, tr.tournament_name, tr.start_date, tr.duration, tr.pre_round, tr.current_round, tr.max_team_size,
                       t.tid AS team_id, t.team_name,
                       tm.points
                FROM "match" m
                JOIN tournament tr ON m.tid = tr.tid
                LEFT JOIN team_match tm ON m.mid = tm.mid
                LEFT JOIN team t ON tm.tid = t.tid
                WHERE m.mid = ?
                """;

        Map<Integer, Match> matchMap = new HashMap<>();
        Map<Integer, Tournament> tournamentCache = new HashMap<>();
        Map<Integer, Team> teamCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setInt(1, id);

            try (ResultSet rs = prepareStatement.executeQuery()) {
                while (rs.next()) {
                    mapResultSetToMatch(rs, matchMap, tournamentCache, teamCache);
                }
            }

            Match match = matchMap.get(id);
            return Optional.ofNullable(match);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to search for match with ID " + id, e);
        }
    }
}
