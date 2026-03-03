package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.repo.MatchRepository;
import org.example.calcettomanagmentsystem.service.repo.PlayerRepository;
import org.example.calcettomanagmentsystem.service.repo.TeamRepository;
import org.example.calcettomanagmentsystem.service.repo.TournamentRepository;
import org.example.calcettomanagmentsystem.service.repo.core.MakerService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MatchService {
    MatchRepository matchRepository;

    public MatchService(TournamentRepository tournamentRepository, MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getAllMatchesFromTournament(@NotNull Tournament tournament) {
        return matchRepository.findAllFromTournament(tournament);
    }

    public List<Match> getAllMatchesFormTournamentOfRound(@NotNull Tournament tournament) {
        return matchRepository.findAllFromTournamentOfRound(tournament, tournament.getCurrendRound());
    }

}
