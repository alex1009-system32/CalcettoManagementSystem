package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface TournamentDao {
	Tournament addTournament(String tournament_name, int duration, int preRound, int maxTeamSize);

	boolean increaseRound(Tournament tournament);

	List<Tournament> getAllTournaments();

	Tournament getTournamentById(int tid);

	boolean deleteTournament(Tournament tournament);
}
