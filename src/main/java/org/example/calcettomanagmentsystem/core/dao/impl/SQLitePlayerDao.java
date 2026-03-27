package org.example.calcettomanagmentsystem.core.dao.impl;

import org.example.calcettomanagmentsystem.core.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.core.dao.PlayerDao;
import org.example.calcettomanagmentsystem.core.DataAccessException;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SQLite implementation of the {@link PlayerDao} interface.
 * <p>
 * This class provides methods to interact with a SQLite database to manage
 * player records, including tournament-specific filtering.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class SQLitePlayerDao implements PlayerDao {
    /** The source providing database connections. */
    DataBaseSource dataBaseSource;

    /**
     * Constructs a new SQLitePlayerDao with the specified data source.
     *
     * @param dataBaseSource The database connection source.
     */
    public SQLitePlayerDao(DataBaseSource dataBaseSource) {
        this.dataBaseSource = dataBaseSource;
    }

    /**
     * Maps a row from a {@link ResultSet} to a {@link Player} object, including its associated {@link Tournament}.
     *
     * @param rs The result set containing player and tournament data.
     * @return A new {@link Player} instance.
     * @throws SQLException If database access fails.
     */
    private Player mapResultSetToPlayer(ResultSet rs) throws SQLException {
        Tournament tournament = new Tournament(rs.getInt("tid"), rs.getString("tournament_name"),
                                               LocalDate.parse(rs.getString("start_date")), rs.getInt("duration"),
                                               rs.getInt("pre_round"), rs.getInt("current_round"),
                                               rs.getInt("max_team_size"));

        return new Player(rs.getInt("pid"), rs.getString("pname"), rs.getString("pemail"), tournament);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> save(Player obj) {
        String sql = "INSERT INTO player (pname, pemail, trid) VALUES (?, ?, ?)";

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.name());
            preparedStatement.setString(2, obj.email());
            preparedStatement.setInt(3, obj.tournament().id());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    return findById(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert player into database.", e);
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Player obj) {
        String sql = "DELETE FROM player WHERE pid = ?";

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, obj.id());
            int affected = preparedStatement.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete player from database.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> findAll() {
        String sql = "SELECT p.pid, p.pname, p.pemail, t.tid, t.tournament_name, t.start_date, t.duration, t.pre_round, t.current_round, t.max_team_size FROM player p JOIN tournament t ON t.tid = p.trid";
        List<Player> players = new ArrayList<>();

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    players.add(mapResultSetToPlayer(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve players from database.", e);
        }

        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getAllPlayersFromTournament(@NotNull Tournament tournament) {
        String sql = "SELECT p.pid, p.pname, p.pemail, t.tid, t.tournament_name, t.start_date, t.duration, t.pre_round, t.current_round, t.max_team_size FROM player p JOIN tournament t ON t.tid = p.trid WHERE t.tid = ?";
        List<Player> players = new ArrayList<>();

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, tournament.id());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    players.add(mapResultSetToPlayer(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve tournament players from database.", e);
        }

        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> findById(int id) {
        String sql = "SELECT p.pid, p.pname, p.pemail, t.tid, t.tournament_name, t.start_date, t.duration, t.pre_round, t.current_round, t.max_team_size FROM player p JOIN tournament t ON t.tid = p.trid WHERE p.pid = ?";
        Player player = null;

        try (Connection connection = dataBaseSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    player = mapResultSetToPlayer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to search for player with ID " + id, e);
        }

        return Optional.ofNullable(player);
    }
}
