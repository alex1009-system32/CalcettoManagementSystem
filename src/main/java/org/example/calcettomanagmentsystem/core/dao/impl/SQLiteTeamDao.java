package org.example.calcettomanagmentsystem.core.dao.impl;

import org.example.calcettomanagmentsystem.core.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.core.dao.TeamDao;
import org.example.calcettomanagmentsystem.shared.exceptions.DataAccessException;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * SQLite implementation of the {@link TeamDao} interface.
 * <p>
 * This class provides methods to interact with a SQLite database to manage
 * team records, player rosters, and tournament associations.
 * </p>
 *
 * @author Senior Developer
 */
public class SQLiteTeamDao implements TeamDao {
    /** The source providing database connections. */
    DataBaseSource dataBaseSource;

    /**
     * Constructs a new SQLiteTeamDao with the specified data source.
     *
     * @param dataBaseSource The database connection source.
     */
    public SQLiteTeamDao(DataBaseSource dataBaseSource) {
        this.dataBaseSource = dataBaseSource;
    }

    /**
     * Maps a row from a {@link ResultSet} to a {@link Team} object, handling tournament and player data.
     *
     * @param rs The result set containing team, player, and tournament data.
     * @param teamCache A map of existing teams to handle multi-player rosters per team row.
     * @param tournamentCache A cache for {@link Tournament} objects.
     * @throws SQLException If database access fails.
     */
    private void mapResultSetToTeam(ResultSet rs,
                                    Map<Integer, Team> teamCache,
                                    Map<Integer, Tournament> tournamentCache) throws SQLException {
        int teamId = rs.getInt("tid");
        Team team = teamCache.computeIfAbsent(teamId, id -> {
            try {
                return new Team(id, rs.getString("team_name"), new ArrayList<>());
            } catch (SQLException e) {
                throw new RuntimeException("Failed to map team from result set.", e);
            }
        });

        int tournamentId = rs.getInt("trid");
        Tournament tournament = null;

        if (!rs.wasNull()) {
            tournament = tournamentCache.computeIfAbsent(tournamentId, id -> {
                try {
                    return new Tournament(id,
                                          rs.getString("tournament_name"),
                                          LocalDate.parse(rs.getString("start_date")),
                                          rs.getInt("duration"),
                                          rs.getInt("pre_round"),
                                          rs.getInt("current_round"),
                                          rs.getInt("max_team_size"));
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to map tournament from result set.", e);
                }
            });
        }

        int playerId = rs.getInt("pid");

        if (!rs.wasNull()) {
            Player player = new Player(playerId, rs.getString("pname"), rs.getString("pemail"), tournament);
            if (!team.players().contains(player)) {
                team.players().add(player);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Team> save(Team obj) {
        String sql = "INSERT INTO team (team_name) VALUES (?)";

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.name());

            int affected = preparedStatement.executeUpdate();
            if (affected == 0) throw new DataAccessException("Failed to insert team record.");

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    return Optional.ofNullable(findById(resultSet.getInt(1)))
                                   .orElseThrow(() -> new DataAccessException("Newly created team not found."));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to save team into database.", e);
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Team addPlayer(Team team, Player player) {
        String sql = "UPDATE player SET tid = ? WHERE pid = ?";

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, team.id());
            preparedStatement.setInt(2, player.id());

            int affected = preparedStatement.executeUpdate();
            if (affected == 0) throw new DataAccessException("Failed to update player's team assignment.");

            if (!team.players().contains(player)) {
                team.players().add(player);
            }

            return team;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to update player in database.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
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

        Map<Integer, Team> teamCache = new LinkedHashMap<>();
        Map<Integer, Tournament> tournamentCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql); 
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                mapResultSetToTeam(resultSet, teamCache, tournamentCache);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to load teams from database.", e);
        }

        return new ArrayList<>(teamCache.values());
    }

    /**
     * {@inheritDoc}
     */
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

        Map<Integer, Team> teamCache = new LinkedHashMap<>();
        Map<Integer, Tournament> tournamentCache = new HashMap<>();

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, tournament.id());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    mapResultSetToTeam(resultSet, teamCache, tournamentCache);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to load tournament teams from database.", e);
        }

        return new ArrayList<>(teamCache.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Team obj) {
        String sql = "DELETE FROM team WHERE tid = ?";

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, obj.id());
            int affected = preparedStatement.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete team from database.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
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

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mapResultSetToTeam(resultSet, teamMap, tournamentCache);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to load team with ID " + id, e);
        }

        return Optional.ofNullable(teamMap.get(id));
    }
}
