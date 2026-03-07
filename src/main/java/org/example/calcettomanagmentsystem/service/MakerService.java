package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.MakerRepository;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MakerService {
    MakerRepository makerRepository;


    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    public List<Match> generateNextMatches(@NotNull Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("tournament id must be greater than 0");

        switch (tournament) {
            case Tournament t when t.id() == 0 -> {
                return makerRepository.generatePreRoundMatches(tournament,
                                                               ServiceManager.getTeamService()
                                                                             .findAllByTournament(tournament));
            }
            case Tournament t when t.preRound() == t.currentRound() -> {
                return makerRepository.gerateRoundMatchesAfterPreRounds(tournament,
                                                                        ServiceManager.getMatchService()
                                                                                      .findMatchesByTournament(
                                                                                              tournament));
            }
            default -> {
                return makerRepository.gerateRoundMatches(tournament,
                                                          ServiceManager.getMatchService()
                                                                        .findMatchesByTournamentInCurrentRound(
                                                                                tournament));
            }
        }
    }
}
