package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.MakerRepository;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class MakerService {
    MakerRepository makerRepository;

    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    public List<Team> generateTeams(Tournament tournament) {
        return makerRepository.generateTeams(ServiceManager.getPlayerService().findAllByTournament(tournament),
                                             tournament.maxTeamSize());
    }

    public List<Match> generateNextMatches(@NotNull Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("tournament id must be greater than 0");


        List<Match> matches;
        switch (tournament) {
            case Tournament t when t.currentRound() == 0 -> {
                matches = generatePreRoundMatches(tournament);
            }
            case Tournament t when t.preRound() == t.currentRound() -> {
                matches = generateRoundMatchesAfterPreRounds(tournament);
            }
            default -> {
                matches = generateRoundMatches(tournament);
            }
        }
        matches.forEach(System.out::println);

        return matches;
    }

    private List<Match> generatePreRoundMatches(@NotNull Tournament tournament) {
        List<Team> teams = ServiceManager.getTeamService().findAllByTournament(tournament);
        return makerRepository.generatePreRoundMatches(tournament, teams);
    }

    private List<Match> generateRoundMatchesAfterPreRounds(@NotNull Tournament tournament) {
        List<Match> matches = ServiceManager.getMatchService().findMatchesByTournament(tournament);

        if (!areAllRoundsCompleted(matches)) throw new ValidationException("matches not finished");

        return makerRepository.gerateRoundMatchesAfterPreRounds(tournament, matches);
    }

    private List<Match> generateRoundMatches(@NotNull Tournament tournament) {
        List<Match> matches = ServiceManager.getMatchService().findMatchesByTournamentInCurrentRound(tournament);

        if (!areAllRoundsCompleted(matches)) throw new ValidationException("matches not finished");

        return makerRepository.gerateRoundMatches(tournament, matches);
    }

    private boolean areAllRoundsCompleted(List<Match> matches) {
        for (Match match : matches) {
            for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
                if (entry.getValue() < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
