package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.MakerRepository;
import org.example.calcettomanagmentsystem.service.repo.MatchRepository;
import org.example.calcettomanagmentsystem.service.repo.PlayerRepository;
import org.example.calcettomanagmentsystem.service.repo.TeamRepository;
import org.example.calcettomanagmentsystem.service.repo.TournamentRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TournamentService {
    TournamentRepository tournamentRepository;
    MakerRepository makerRepository;

    public TournamentService(TournamentRepository tournamentRepository,
                             MakerRepository makerRepository) {
        this.tournamentRepository = tournamentRepository;
        this.makerRepository = makerRepository;
    }

    public Tournament createTournament(@NotNull String name, long duration, int preRound, int maxTeamSize) {
        if (name == "") throw new ValidationException("Tournament name cannot be empty");
        if (name.matches(" ")) throw new ValidationException("Tournament name cannot contain spaces");
        if (name.matches("!\"#$%&'()*+,-./:;<=>?@[\\]^_{|}~")) throw new ValidationException("Tournament name cannot contain Special Characters");

        if (duration < 1) throw new ValidationException("Tournament duration cannot be less than 1");
        if (preRound < 1) throw new ValidationException("Tournament preRound cannot be less than 1");
        if (maxTeamSize < 1) throw new ValidationException("Tournament maxTeamSize cannot be less than 1");

        if (maxTeamSize > 11) throw new ValidationException("Tournament maxTeamSize cannot be greater than 11");

        return tournamentRepository.save(new Tournament(name, duration, preRound, maxTeamSize));
    }

    public boolean deleteTournament(@NotNull Tournament tournament) {
        if (tournament.getTid() < 0) throw new ValidationException("Tournament id cannot be less than 0");

        return tournamentRepository.delete(tournament);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public boolean startTournament(@NotNull Tournament tournament) {
        if (tournament.getCurrendRound() != 0) throw new ValidationException("Invalid current round");

        makerRepository.generateTeams(tournament);

        return makerRepository.generatePreRoundMatches(tournament);
    }

    public boolean nextRound(@NotNull Tournament tournament) {
        if (tournament.getCurrendRound() < 0) throw new ValidationException("Invalid current round");
        if (tournament.getCurrendRound() < tournament.getPreRound()) throw new ValidationException("Invalid current round");

        return makerRepository.generateRoundMatches(tournament);
    }

}
