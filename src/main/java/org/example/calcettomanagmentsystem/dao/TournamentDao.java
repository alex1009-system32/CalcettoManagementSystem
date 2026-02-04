package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface TournamentDao {
    void addTournament(Tournament tournament);
    List<Tournament> getAllTournaments();
    Tournament getTournamentById(int tid);
}
