package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteMatchDao implements MatchDao {

    private Connection connection;

    public SQLiteMatchDao() {
        try {
            this.connection = SQLiteDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMatch(Tournament tournament, int round) {
        String sql = "INSERT INTO match (round, tid) VALUES (?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(2, tournament.getTid());
            preparedStatement.setInt(1, round);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTeamToMatch(Team team, double points, Match match) {
        String sql = "INSERT INTO team_match(tid, points, mid) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, team.getTid());
            preparedStatement.setDouble(2, points);
            preparedStatement.setInt(3, match.getMid());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Match> getAllMatchesFromTournament(Tournament tournament) {
        String sql =  "SELECT * FROM match WHERE tid = ?";

        // ToDo go on thurdure right here

        return new ArrayList<Match>(){{}};
    }

    @Override
    public List<Match> getAllMatchesFromTeam(Team team) {
        return List.of();
    }

    @Override
    public Match getMatchById(int mid) {
        return null;
    }

    // ToDo need to go further here!!!!!
}
