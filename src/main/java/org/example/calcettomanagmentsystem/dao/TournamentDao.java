package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface TournamentDao {
    void addTournament(String tournamentName, int duration);
    List<Tournament> getAllTournaments();
    Tournament getTournamentById(int tid);
}
