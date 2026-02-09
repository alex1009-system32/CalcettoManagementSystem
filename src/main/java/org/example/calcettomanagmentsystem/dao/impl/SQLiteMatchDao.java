package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public void addMatch(Match match) {

    }

    @Override
    public List<Match> getAllMatchesFromTournament(Tournament tournament) {
        return List.of();
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
