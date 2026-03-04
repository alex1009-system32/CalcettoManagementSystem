package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTournamentDao implements TournamentDao {

    private Tournament mapResultSetToTournament(ResultSet rs) throws SQLException {
        return new Tournament(rs.getInt("tid"),
                              rs.getString("tournament_name"),
                              LocalDate.parse(rs.getString("start_date")),
                              rs.getInt("duration"),
                              rs.getInt("pre_round"),
                              rs.getInt("current_round"),
                              rs.getInt("max_team_size"));
    }

    @Override
    public Tournament save(@NotNull Tournament obj) {
        String sql =
                "INSERT INTO tournament (tournament_name, start_date, duration, pre_round, current_round, max_team_size) VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection connection = SQLiteDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setString(1, obj.getTournamentName());
            preparedStatement.setString(2, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(obj.getDate()));
            preparedStatement.setLong(3, obj.getDuration());
            preparedStatement.setInt(4, obj.getPreRound());
            preparedStatement.setInt(5, obj.getCurrentRound());
            preparedStatement.setInt(6, obj.getMaxTeamSize());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                return findById(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error Inserting Into tournament from the database", e);
        }

        return null;
    }

    @Override
    public List<Tournament> findAll() {
        String sql = "SELECT * FROM tournament";

        List<Tournament> tournaments = new ArrayList<>();

        try (Connection connection = SQLiteDB.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(
                sql)) {
            while (resultSet.next()) {
                tournaments.add(mapResultSetToTournament(resultSet));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error Finding All Tournament from the database", e);
        }

        return tournaments;
    }

    @Override
    public boolean delete(Tournament obj) {
        String sql = "DELETE FROM tournament WHERE tid = ?";

        try (Connection connection = SQLiteDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, obj.getTid());
            int affected = preparedStatement.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error Deleting Tournament from the database", e);
        }
    }

    @Override
    public Tournament findById(int id) {
        String sql = "SELECT * FROM tournament WHERE tid = ?";

        Tournament tournament = null;

        try (Connection connection = SQLiteDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tournament = mapResultSetToTournament(resultSet);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error Finding Tournament from the database", e);
        }

        return tournament;
    }

    @Override
    public Tournament increaseRound(Tournament tournament) {
        String sql = "UPDATE tournament SET current_round = ? WHERE tid = ?";

        try (Connection connection = SQLiteDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, tournament.getCurrentRound() + 1);
            preparedStatement.setInt(2, tournament.getTid());
            preparedStatement.executeUpdate();

            tournament.setCurrentRound(tournament.getCurrentRound() + 1);
            return tournament;
        } catch (SQLException e) {
            throw new DataAccessException("Error Inserting Into tournament from the database", e);
        }

    }
}
