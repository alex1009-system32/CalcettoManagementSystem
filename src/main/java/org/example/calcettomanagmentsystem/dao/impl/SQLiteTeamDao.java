package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTeamDao implements TeamDao {

    private Connection connection;

    public SQLiteTeamDao() {
        try {
            this.connection = SQLiteDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTeamFromTournament(String teamname, Tournament tournament) {
        String sql = "INSERT INTO match (round, tid) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, teamname);
            preparedStatement.setInt(2, tournament.getTid());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Team> getAllTeamsFromTournament(Tournament tournament) {
        String sql = "SELECT * FROM team WHERE trid = ?";
        String innerSql = "SELECT * FROM player WHERE tid = ?";

        Team t;
        List<Team> teams = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, tournament.getTid());

            ResultSet resultSet = preparedStatement.executeQuery();
            PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql);

            while (resultSet.next()) {

                t = new Team(
                        resultSet.getInt("tid"),
                        resultSet.getString("teamName")
                );

                innerPreparedStatement.setInt(
                        1,
                        resultSet.getInt("tid")
                );

                ResultSet innerResultSet = innerPreparedStatement.executeQuery();

                while (innerResultSet.next()) {
                    t.addPlayer(
                            new Player(
                                    innerResultSet.getInt("pid"),
                                    innerResultSet.getString("pname"),
                                    innerResultSet.getString("pemail")
                            )
                    );
                }

                teams.add(t);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    @Override
    public Team getTeamById(int tid) {
        String sql = "SELECT * FROM team WHERE trid = ?";
        String innerSql = "SELECT * FROM player WHERE tid = ?";

        Team team = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(
                    1,
                    tid
            );

            ResultSet resultSet = preparedStatement.executeQuery();
            PreparedStatement innerPreparedStatement = connection.prepareStatement(innerSql);

            while (resultSet.next()) {

                team = new Team(
                        resultSet.getInt("tid"),
                        resultSet.getString("teamName")
                );

                innerPreparedStatement.setInt(
                        1,
                        resultSet.getInt("tid")
                );

                ResultSet innerResultSet = innerPreparedStatement.executeQuery();

                while (innerResultSet.next()) {
                    team.addPlayer(
                            new Player(
                                    innerResultSet.getInt("pid"),
                                    innerResultSet.getString("pname"),
                                    innerResultSet.getString("pemail")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return team;
    }
}
