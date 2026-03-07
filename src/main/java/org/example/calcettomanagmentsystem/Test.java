package org.example.calcettomanagmentsystem;


import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamShuffler;
import org.example.calcettomanagmentsystem.core.WinnerExtractor;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.example.calcettomanagmentsystem.service.repo.MatchRepository;

import java.sql.SQLException;
import java.util.ArrayList;
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

        SQLiteDB.initTest();

        Tournament tournament = ServiceManager.getTournamentService().findAll().getLast();
        List<Team> team = ServiceManager.getTeamService().findAllByTournament(tournament);
        //new MatchMaker(new TeamShuffler(), new WinnerExtractor()).makePreRounds(tournament, team).forEach(System.out::println);

        ServiceManager.getMakerService().generateNextMatches(tournament);

    }

}
