package org.example.calcettomanagmentsystem.service.interfaces;

import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.Optional;

public interface TournamentRepository extends Repository<Tournament> {
    Optional<Tournament> increaseRound(Tournament tournament);
}
