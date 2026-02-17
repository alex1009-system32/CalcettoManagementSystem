package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface TournamentDao {
	void addTournament(String tournament_name, int duration, int preRound, int maxTeamSize);

	void increaseRound(Tournament tournament);

	List<Tournament> getAllTournaments();

	Tournament getTournamentById(int tid);
}
