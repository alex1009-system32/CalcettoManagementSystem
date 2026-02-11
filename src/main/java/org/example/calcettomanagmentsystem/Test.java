package org.example.calcettomanagmentsystem;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Test {

	static void main() {

		System.out.println(new SQLiteMatchDao().getMatchById(2));

	}

}
