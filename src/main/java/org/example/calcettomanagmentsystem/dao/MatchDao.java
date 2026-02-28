package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

/**
 * Data access contract for managing matches and their relationships.
 */
public interface MatchDao {
	/**
	 * Persists a new match for the given tournament and round.
	 *
	 * @param tournament owning tournament
	 * @return created match
	 */
	Match addMatch(Tournament tournament);

	/**
	 * Adds a team to a match and stores its points.
	 *
	 * @param team team to add
	 * @param match target match
	 * @return true if successful
	 */
	boolean addTeamToMatch(Team team,  Match match);

	/**
	 * Adds a team to a match and stores its points.
	 *
	 * @param team team to add
	 * @param match target match
	 * @return true if successful
	 */
	boolean addAddPointToTeamInMatch(Team team,  Match match,double point);

	/**
	 * Loads all matches for a tournament.
	 *
	 * @param tournament filter tournament
	 * @return list of matches
	 */
	List<Match> getAllMatchesFromTournament(Tournament tournament);

	List<Match> getAllMatchesFromTournamentInRound(Tournament tournament, int round);

	/**
	 * Loads all matches a given team participated in.
	 *
	 * @param team filter team
	 * @return list of matches
	 */
	List<Match> getAllMatchesFromTeam(Team team);

	/**
	 * Retrieves a match by its id.
	 *
	 * @param mid match id
	 * @return match or null if not found
	 */
	Match getMatchById(int mid);

	/**
	 * Deletes a match.
	 *
	 * @param match match to delete
	 * @return true if deletion succeeded
	 */
	boolean deleteMatch(Match match);
}
