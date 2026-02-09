package org.example.calcettomanagmentsystem.dao.impl;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.PlayerDao;
import org.example.calcettomanagmentsystem.model.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SQLitePlayerDao implements PlayerDao {

    private Connection connection;

    public SQLitePlayerDao() {
        try {
            this.connection = SQLiteDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addPlayer(String pname, String pemail) {

    }

    @Override
    public List<Player> getAllPlayers() {
        return List.of();
    }

    @Override
    public Player getPlayerById(int pid) {
        return null;
    }

    // ToDo need to go further here!!!!!
}
