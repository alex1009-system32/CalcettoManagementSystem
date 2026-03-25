package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLiteTournamentDao implements TournamentDao {
    DataBaseSource dataBaseSource;

    public SQLiteTournamentDao(DataBaseSource dataBaseSource) {
        this.dataBaseSource = dataBaseSource;
    }

    private Tournament mapResultSetToTournament(ResultSet rs) throws SQLException {
        return new Tournament(rs.getInt("tid"), rs.getString("tournament_name"), LocalDate.parse(rs.getString("start_date")), rs.getInt("duration"), rs.getInt("pre_round"), rs.getInt("current_round"), rs.getInt("max_team_size"));
    }

    @Override
    public Optional<Tournament> save(@NotNull Tournament obj) {
        String sql = "INSERT INTO tournament (tournament_name, start_date, duration, pre_round, current_round, max_team_size) VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, obj.name());
            preparedStatement.setString(2, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(obj.date()));
            preparedStatement.setLong(3, obj.duration());
            preparedStatement.setInt(4, obj.preRound());
            preparedStatement.setInt(5, obj.currentRound());
            preparedStatement.setInt(6, obj.maxTeamSize());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    return Optional.of(new Tournament(resultSet.getInt(1), obj.name(), obj.date(), obj.duration(), obj.preRound(), obj.currentRound(), obj.maxTeamSize()));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error Inserting Into tournament from the database", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Tournament> findAll() {
        String sql = "SELECT * FROM tournament";
        List<Tournament> tournaments = new ArrayList<>();

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tournaments.add(mapResultSetToTournament(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error Finding All Tournament from the database", e);
        }

        return tournaments;
    }

    @Override
    public boolean delete(Tournament obj) {
        String sql = "DELETE FROM tournament WHERE tid = ?";

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, obj.id());
            int affected = preparedStatement.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error Deleting Tournament from the database", e);
        }
    }

    @Override
    public Optional<Tournament> findById(int id) {
        String sql = "SELECT * FROM tournament WHERE tid = ?";

        Tournament tournament = null;

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tournament = mapResultSetToTournament(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error Finding Tournament from the database", e);
        }

        return Optional.ofNullable(tournament);
    }

    @Override
    public Tournament increaseRound(Tournament tournament) {
        String sql = "UPDATE tournament SET current_round = ? WHERE tid = ?";

        try (Connection connection = dataBaseSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int newCurrentRound = tournament.currentRound() + 1;

            preparedStatement.setInt(1, newCurrentRound);
            preparedStatement.setInt(2, tournament.id());
            preparedStatement.executeUpdate();

            return new Tournament(tournament.id(), tournament.name(), tournament.date(), tournament.duration(), tournament.preRound(), newCurrentRound, tournament.maxTeamSize());
        } catch (SQLException e) {
            throw new DataAccessException("Error Inserting Into tournament from the database", e);
        }
    }
}
