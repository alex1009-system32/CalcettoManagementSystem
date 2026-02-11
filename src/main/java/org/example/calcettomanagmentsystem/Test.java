package org.example.calcettomanagmentsystem;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Test {

    static void main() {

        SQLitePlayerDao sqliteDao = new SQLitePlayerDao();

        List<Player> p = sqliteDao.getAllPlayers();

        for (Player p1 : p) {
            System.out.println(p1);
        }

    }

}
