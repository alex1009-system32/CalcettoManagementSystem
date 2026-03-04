package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.repo.MatchRepository;
import org.example.calcettomanagmentsystem.service.repo.TournamentRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MatchService {
    MatchRepository matchRepository;

    public MatchService(TournamentRepository tournamentRepository, MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> findMatchesByTournament(@NotNull Tournament tournament) {
        return matchRepository.findMatchesByTournament(tournament);
    }

    public List<Match> findMatchesByTournamentInCurrentRound(@NotNull Tournament tournament) {
        return matchRepository.findMatchesByTournament(tournament, tournament.getCurrentRound());
    }

}
