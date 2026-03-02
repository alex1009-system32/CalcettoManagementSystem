package org.example.calcettomanagmentsystem;

import com.github.javafaker.Faker;
import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lokaler Einstiegspunkt für manuelle Experimente und Datenbankchecks.
 * <p>
 * Die Klasse ist bewusst nicht Teil der Produktivlogik und dient
 * zur schnellen Validierung während der Entwicklung.
 * </p>
 */
public class Test {

	/**
	 * Führt einfache Laufzeitprüfungen gegen die Datenbank aus.
	 *
	 * @param args Prozessargumente, derzeit ohne Auswertung
	 * @throws SQLException wenn der Datenbankzugriff fehlschlägt
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

		//new SQLiteTeamDao().getAllTeamsFromTournament(new SQLiteTournamentDao().getTournamentById(11)).forEach(System.out::println);

		SQLiteDB.initTest();

	}

}
