package org.example.calcettomanagmentsystem;

import com.github.javafaker.Faker;
import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Ad-hoc test harness for local experiments and database initialization.
 */
public class Test {

	/**
	 * Initializes the test database schema and sample data.
	 */
	public static void main(String[] args) throws SQLException {
		/*
		List<String> deck = new ArrayList<>();
		deck.add("Ace");
		deck.add("King");
		deck.add("Queen");
		deck.add("Jack");

		// Shuffle the list
		Collections.shuffle(deck);

		System.out.println(deck);
		 */

		SQLiteDB.initTest();
	}

}
