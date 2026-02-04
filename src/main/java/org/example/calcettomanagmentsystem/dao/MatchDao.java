package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface MatchDao {
    void addMatch(Match match);
    List<Match> getAllMatchesFromTournament(Tournament tournament);
    List<Match> getAllMatchesFromTeam(Team team);
    Match getMatchById(int id);
}
