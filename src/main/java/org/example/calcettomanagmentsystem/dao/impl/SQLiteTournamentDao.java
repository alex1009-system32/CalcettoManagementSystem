package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SQLiteTournamentDao implements TournamentDao {

    private Connection connection;

    public SQLiteTournamentDao() {
        try {
           this.connection = SQLiteDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTournament(Tournament tournament) {

    }

    @Override
    public List<Tournament> getAllTournaments() {
        return List.of();
    }

    @Override
    public Tournament getTournamentById(int tid) {
        return null;
    }

    // ToDo need to go further here!!!!!

}
