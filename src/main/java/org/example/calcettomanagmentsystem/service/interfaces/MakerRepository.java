package org.example.calcettomanagmentsystem.service.interfaces;

import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.List;

public interface MakerRepository {
     List<Team> generateTeams(List<Player> players, int teamSize);
     List<Match> generatePreRoundMatches(Tournament tournament, List<Team> teams);
     List<Match> gerateRoundMatchesAfterPreRounds(Tournament tournament, List<Match> matches);
     List<Match> gerateRoundMatches(Tournament tournament, List<Match> matches);
}
