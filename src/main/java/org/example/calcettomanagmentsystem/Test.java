package org.example.calcettomanagmentsystem;


import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.sql.SQLException;
import java.util.List;


public class Test {

    
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

        //SQLiteDB.initTest();

        SQLiteDB.initTest();

        List<Tournament> torunaments = ServiceManager.getTournamentService().findAll();
        List<Player> players = ServiceManager.getPlayerService().findAllOfTournament(torunaments.getFirst());

        ServiceManager.getMakerService().generateTeams(torunaments.getFirst());
        ServiceManager.getMakerService().generateNextMatches(torunaments.getFirst());

        ServiceManager.getMatchService().findMatchesByTournament(torunaments.getFirst()).forEach(System.out::println);

    }

}
