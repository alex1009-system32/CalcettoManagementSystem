package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.Connection;
import java.sql.SQLException;
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

    }

    @Override
    public void addPlayerToTeam(int tid, Player player) {

    }

    @Override
    public void addPlayerToTeam(String teamname, Player player) {

    }

    @Override
    public List<Team> getAllTeamsFromTournament(Tournament tournament) {
        return List.of();
    }

    @Override
    public Team getTeamById(int tid) {
        return null;
    }

    // ToDo need to go further here!!!!!
}
