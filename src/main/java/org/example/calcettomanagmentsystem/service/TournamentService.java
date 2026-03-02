package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.model.Tournament;

public interface TournamentService {
    void generateTeams();
    void generatePreRoundMatches();
    void generateRoundMatches();
}
