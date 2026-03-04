package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MatchDao extends GeneralDao<Match> {
    boolean registerTeam(Team team, Match match);
    boolean assignPoints(Team team, double point, Match match);
    List<Match> findMatchesByTournament(@NotNull Tournament tournament);
    List<Match> findMatchesByTournament(@NotNull Tournament tournament, int round);
}

