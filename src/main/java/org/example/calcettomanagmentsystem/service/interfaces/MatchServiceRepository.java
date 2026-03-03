package org.example.calcettomanagmentsystem.service.interfaces;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface MatchServiceRepository {
    List<Match> findAllFromTournament(Tournament tournament);

    List<Match> findAllFromTournamentOfRound(Tournament tournament, int round);
}
