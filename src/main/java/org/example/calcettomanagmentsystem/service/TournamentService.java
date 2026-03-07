package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.MakerRepository;
import org.example.calcettomanagmentsystem.service.repo.TournamentRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TournamentService {
    TournamentRepository tournamentRepository;
    MakerRepository makerRepository;

    public TournamentService(TournamentRepository tournamentRepository, MakerRepository makerRepository) {
        this.tournamentRepository = tournamentRepository;
        this.makerRepository = makerRepository;
    }

    public Tournament create(@NotNull String name, long duration, int preRound, int maxTeamSize) {
        if (name == null || name.isBlank()) throw new ValidationException("Name darf nicht leer sein");
        if (!name.matches("^[a-zA-Z0-9]*$")) throw new ValidationException("Name enthält ungültige Sonderzeichen");
        if (maxTeamSize < 1) throw new ValidationException("Ein Team braucht mindestens 2 Spieler");

        return tournamentRepository.save(new Tournament(name, duration, preRound, maxTeamSize)).orElseThrow(() -> new DataAccessException("Could not save tournament"));
    }

    public Tournament increaseRound(Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("Tournament id cannot be less than 0");

        return tournamentRepository.increaseRound(tournament).orElseThrow(() -> new DataAccessException("Could not find tournament"));
    }

    public boolean delete(@NotNull Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("Tournament id cannot be less than 0");

        return tournamentRepository.delete(tournament);
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public boolean start(@NotNull Tournament tournament) {
        return false;
    }

}
