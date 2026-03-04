package org.example.calcettomanagmentsystem;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.sql.SQLException;

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
    public static void main(String[] args) throws Exception {
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

        ServiceManager.getTournamentService().getAllTournaments().forEach(System.out::println);

    }

}
