package org.example.calcettomanagmentsystem;


import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamShuffler;
import org.example.calcettomanagmentsystem.core.WinnerExtractor;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Test {
    static void main(String[] args) {
        //new SQLiteDB().initTest();

        Tournament tournament = new Tournament(1, "Tournament1", 22, 2, 2);

        Player p1 = new Player(1, "Player1", "p1@mail.org", tournament);
        Player p2 = new Player(2, "Player2", "p2@mail.org", tournament);
        Player p3 = new Player(3, "Player3", "p3@mail.org", tournament);
        Player p4 = new Player(4, "Player4", "p4@mail.org", tournament);
        Player p5 = new Player(5, "Player5", "p5@mail.org", tournament);
        Player p6 = new Player(6, "Player6", "p6@mail.org", tournament);
        Player p7 = new Player(7, "Player7", "p7@mail.org", tournament);
        Player p8 = new Player(8, "Player8", "p8@mail.org", tournament);
        Player p9 = new Player(9, "Player9", "p9@mail.org", tournament);
        Player p10 = new Player(10, "Player10", "p10@mail.org", tournament);
        Player p11 = new Player(11, "Player11", "p11@mail.org", tournament);
        Player p12 = new Player(12, "Player12", "p12@mail.org", tournament);
        Player p13 = new Player(13, "Player13", "p13@mail.org", tournament);
        Player p14 = new Player(14, "Player14", "p14@mail.org", tournament);
        Player p15 = new Player(15, "Player15", "p15@mail.org", tournament);
        Player p16 = new Player(16, "Player16", "p16@mail.org", tournament);

        Team t1 = new Team(1, "Team1", new ArrayList<>());
        Team t2 = new Team(2, "Team2", new ArrayList<>());
        Team t3 = new Team(3, "Team3", new ArrayList<>());
        Team t4 = new Team(4, "Team4", new ArrayList<>());
        Team t5 = new Team(5, "Team5", new ArrayList<>());
        Team t6 = new Team(6, "Team6", new ArrayList<>());
        Team t7 = new Team(7, "Team7", new ArrayList<>());
        Team t8 = new Team(8, "Team8", new ArrayList<>());

        t1.players().add(p1);
        t1.players().add(p2);
        t2.players().add(p3);
        t2.players().add(p4);
        t3.players().add(p5);
        t3.players().add(p6);
        t4.players().add(p7);
        t4.players().add(p8);
        t5.players().add(p9);
        t5.players().add(p10);
        t6.players().add(p11);
        t6.players().add(p12);
        t7.players().add(p13);
        t7.players().add(p14);
        t8.players().add(p15);
        t8.players().add(p16);

        Match m1 = new Match(1, 1, tournament);
        Match m2 = new Match(2, 1, tournament);
        Match m3 = new Match(3, 1, tournament);
        Match m4 = new Match(4, 1, tournament);
        Match m5 = new Match(5, 2, tournament);
        Match m6 = new Match(6, 2, tournament);
        Match m7 = new Match(7, 2, tournament);
        Match m8 = new Match(8, 2, tournament);

        m1.teamResults().put(t1, 45.0);
        m1.teamResults().put(t2, 45.0);

        m2.teamResults().put(t3, 44.0);
        m2.teamResults().put(t4, 44.0);

        m3.teamResults().put(t5, 42.0);
        m3.teamResults().put(t6, 42.0);

        m4.teamResults().put(t7, 41.0);
        m4.teamResults().put(t8, 41.0);

        m5.teamResults().put(t1, 40.0);
        m5.teamResults().put(t3, 40.0);

        m6.teamResults().put(t2, 39.0);
        m6.teamResults().put(t4, 39.0);

        m7.teamResults().put(t5, 38.0);
        m7.teamResults().put(t7, 38.0);

        m8.teamResults().put(t6, 37.0);
        m8.teamResults().put(t8, 37.0);

        List<Match> matches = new ArrayList<>();

        matches.add(m1);
        matches.add(m2);
        matches.add(m3);
        matches.add(m4);
        matches.add(m5);
        matches.add(m6);
        matches.add(m7);
        matches.add(m8);

        TeamShuffler teamShuffler = new TeamShuffler();
        WinnerExtractor winnerExtractor = new WinnerExtractor();
        MatchMaker maker = new MatchMaker(teamShuffler, winnerExtractor);

        List<Match> newMatches = maker.makeMatchesForRoundAfterPreRounds(
                tournament, matches);

        // Next Round
        Match m9 = new Match(9, 1, tournament);
        Match m10 = new Match(10, 1, tournament);
        Match m11 = new Match(11, 1, tournament);
        Match m12 = new Match(12, 1, tournament);

        m9.teamResults().put(t1, 12.0);
        m9.teamResults().put(t8, 0.0);
        m10.teamResults().put(t2, 12.0);
        m10.teamResults().put(t7, 0.0);
        m11.teamResults().put(t3, 12.0);
        m11.teamResults().put(t6, 0.0);
        m12.teamResults().put(t4, 12.0);
        m12.teamResults().put(t5, 0.0);

        List<Match> matches2 = new ArrayList<>();
        matches2.add(m9);
        matches2.add(m10);
        matches2.add(m11);
        matches2.add(m12);

        Tournament tournament2 = new Tournament(1, "Tournament1", 22, 2, 2,2);

        List<Match> newMatches2 = maker.makeMatchesForRound(tournament2, matches2);

        newMatches2.forEach(System.out::println);

    }
}
