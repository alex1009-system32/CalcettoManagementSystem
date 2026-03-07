package org.example.calcettomanagmentsystem.service.interfaces;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface MatchRepository extends Repository<Match> {
    Match addTeam(Team team, Match match);
    Match addPoints(Team team, Match match, int points);
    List<Match> findMatchesByTournament(Tournament tournament);
    List<Match> findMatchesByTournament(Tournament tournament, int round);
}
