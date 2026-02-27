package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

/**
 * Data access contract for creating, updating and retrieving tournaments.
 */
public interface TournamentDao {
	/**
	 * Persists a new tournament.
	 *
	 * @param tournament_name name of the tournament
	 * @param duration duration in days
	 * @param preRound number of preliminary rounds
	 * @param maxTeamSize max players per team
	 * @return created tournament
	 */
	Tournament addTournament(String tournament_name, int duration, int preRound, int maxTeamSize);

	/**
	 * Increments the current round counter of the given tournament.
	 *
	 * @param tournament tournament to update
	 * @return true if the update succeeded
	 */
	boolean increaseRound(Tournament tournament);

	/**
	 * Loads all tournaments.
	 *
	 * @return list of tournaments
	 */
	List<Tournament> getAllTournaments();

	/**
	 * Retrieves a tournament by id.
	 *
	 * @param tid tournament id
	 * @return tournament or null if not found
	 */
	Tournament getTournamentById(int tid);

	/**
	 * Deletes a tournament.
	 *
	 * @param tournament tournament to delete
	 * @return true if deletion succeeded
	 */
	boolean deleteTournament(Tournament tournament);
}
