package org.example.calcettomanagmentsystem.core.dao.impl;

import org.example.calcettomanagmentsystem.core.DataAccessException;
import org.example.calcettomanagmentsystem.core.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.core.dao.TournamentDao;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SQLite implementation of the {@link TournamentDao} interface.
 * <p>
 * This class provides methods to interact with a SQLite database to manage
 * tournament records and round progression.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class SQLiteTournamentDao implements TournamentDao {
    /**
     * The source providing database connections.
     */
    DataBaseSource dataBaseSource;

    /**
     * Constructs a new SQLiteTournamentDao with the specified data source.
     *
     * @param dataBaseSource The database connection source.
     */
    public SQLiteTournamentDao(DataBaseSource dataBaseSource) {
        this.dataBaseSource = dataBaseSource;
    }

    /**
     * Maps a row from a {@link ResultSet} to a {@link Tournament} object.
     *
     * @param rs The result set containing tournament data.
     * @return A new {@link Tournament} instance.
     * @throws SQLException If database access fails.
     */
    private Tournament mapResultSetToTournament(ResultSet rs) throws SQLException {
        return new Tournament(rs.getInt("tid"),
                              rs.getString("tournament_name"),
                              LocalDate.parse(rs.getString("start_date")),
                              rs.getInt("duration"),
                              rs.getInt("pre_round"),
                              rs.getInt("current_round"),
                              rs.getInt("max_team_size"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tournament> save(@NotNull Tournament obj) {
        String sql =
                "INSERT INTO tournament (tournament_name, start_date, duration, pre_round, current_round, max_team_size) VALUES (?, ?, ?, ?, ?, ?);";

        try {
            Connection connection = dataBaseSource.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                                                                                   Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, obj.name());
                preparedStatement.setString(2, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(obj.date()));
                preparedStatement.setLong(3, obj.duration());
                preparedStatement.setInt(4, obj.preRound());
                preparedStatement.setInt(5, obj.currentRound());
                preparedStatement.setInt(6, obj.maxTeamSize());

                preparedStatement.executeUpdate();

                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        return Optional.of(new Tournament(resultSet.getInt(1),
                                                          obj.name(),
                                                          obj.date(),
                                                          obj.duration(),
                                                          obj.preRound(),
                                                          obj.currentRound(),
                                                          obj.maxTeamSize()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert tournament record into database.", e);
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament increaseRound(Tournament tournament) {
        String sql = "UPDATE tournament SET current_round = ? WHERE tid = ?";

        try {
            Connection connection = dataBaseSource.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int newCurrentRound = tournament.currentRound() + 1;

                preparedStatement.setInt(1, newCurrentRound);
                preparedStatement.setInt(2, tournament.id());
                preparedStatement.executeUpdate();

                return new Tournament(tournament.id(),
                                      tournament.name(),
                                      tournament.date(),
                                      tournament.duration(),
                                      tournament.preRound(),
                                      newCurrentRound,
                                      tournament.maxTeamSize());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update tournament round in database.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tournament> findAll() {
        String sql = "SELECT * FROM tournament";
        List<Tournament> tournaments = new ArrayList<>();

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tournaments.add(mapResultSetToTournament(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve all tournaments from database.", e);
        }

        return tournaments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Tournament obj) {
        String sql = "DELETE FROM tournament WHERE tid = ?";

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, obj.id());
            int affected = preparedStatement.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete tournament from database.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tournament> findById(int id) {
        String sql = "SELECT * FROM tournament WHERE tid = ?";

        Tournament tournament = null;

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tournament = mapResultSetToTournament(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to search for tournament with ID " + id, e);
        }

        return Optional.ofNullable(tournament);
    }
}
