package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.dao.PlayerDao;
import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SQLite-spezifischer Zugriff auf Spieler.
 * <p>
 * Die Implementierung kapselt SQL-Details, damit die Aufrufer
 * ausschließlich mit Domänenobjekten arbeiten.
 * </p>
 *
 * @see PlayerDao
 */
public class SQLitePlayerDao implements PlayerDao {
    DataBaseSource dataBaseSource;

    public SQLitePlayerDao(DataBaseSource dataBaseSource) {
        this.dataBaseSource = dataBaseSource;
    }

    private Player mapResultSetToPlayer(ResultSet rs) throws SQLException {
        Tournament tournament = new Tournament(rs.getInt("tid"),
                                               rs.getString("tournament_name"),
                                               LocalDate.parse(rs.getString("start_date")),
                                               rs.getInt("duration"),
                                               rs.getInt("pre_round"),
                                               rs.getInt("current_round"),
                                               rs.getInt("max_team_size"));

        return new Player(rs.getInt("pid"), rs.getString("pname"), rs.getString("pemail"), tournament);

    }

    @Override
    public Optional<Player> save(Player obj) {
        String sql = "INSERT INTO player (pname, pemail, trid) VALUES (?, ?, ?)";

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setString(1, obj.name());
            preparedStatement.setString(2, obj.email());
            preparedStatement.setInt(3, obj.tournament().id());

            System.out.println(obj);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                return findById(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error Inserting Into Player form the database", e);
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Player obj) {
        String sql = "DELETE FROM player WHERE pid = ?";

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, obj.id());
            int affected = preparedStatement.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error Deleting Player from the database", e);
        }
    }

    @Override
    public List<Player> findAll() {
        String sql = "SELECT p.pid, p.pname, p.pemail, t.tid, t.tournament_name, t.start_date, t.duration, t.pre_round, t.current_round, t.max_team_size FROM player p JOIN tournament t ON t.tid = p.trid";
        List<Player> players = new ArrayList<>();

        try (Connection connection = dataBaseSource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                players.add(mapResultSetToPlayer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error Finding Player from the database");
        }

        return players;
    }

    @Override
    public List<Player> getAllPlayersFromTournament(@NotNull Tournament tournament) {
        String sql = "SELECT p.pid, p.pname, p.pemail, t.tid, t.tournament_name, t.start_date, t.duration, t.pre_round, t.current_round, t.max_team_size FROM player p JOIN tournament t ON t.tid = p.trid WHERE t.tid = ?";
        List<Player> players = new ArrayList<>();

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, tournament.id());
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
                players.add(mapResultSetToPlayer(resultSet));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error Finding Player from the database");
        }

        return players;
    }

    @Override
    public Optional<Player> findById(int id) {
        String sql = "SELECT p.pid, p.pname, p.pemail, t.tid, t.tournament_name, t.start_date, t.duration, t.pre_round, t.current_round, t.max_team_size FROM player p JOIN tournament t ON t.tid = p.trid WHERE p.pid = ?";
        Player player = null;

        try (Connection connection = dataBaseSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                player = mapResultSetToPlayer(resultSet);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error Finding Player from the database", e);
        }

        return Optional.ofNullable(player);

    }
}
