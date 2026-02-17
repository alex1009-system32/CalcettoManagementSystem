package org.example.calcettomanagmentsystem;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.SQLException;
import java.util.List;

public class Test {

	static void main() throws SQLException {

		SQLiteDB.initTest();

	}

}
