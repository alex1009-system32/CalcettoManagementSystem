package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

/**
 * Data access contract for managing teams and their player memberships.
 */
public interface TeamDao {
	/**
	 * Creates and persists a new team.
	 *
	 * @param teamname name of the team
	 * @return created team
	 */
	Team addTeam(String teamname);

	/**
	 * Adds a player to a team and persists the membership.
	 *
	 * @param player player to add
	 * @param team team to join
	 * @return true if successful
	 */
	boolean addPlayerToTeam(Player player, Team team);

	/**
	 * Loads all teams participating in a given tournament.
	 *
	 * @param tournament filter tournament
	 * @return list of teams
	 */
	List<Team> getAllTeamsFromTournament(Tournament tournament);

	/**
	 * Finds a team by id.
	 *
	 * @param tid team id
	 * @return team or null if not found
	 */
	Team getTeamById(int tid);

	/**
	 * Finds a team by its unique name.
	 *
	 * @param teamName unique team name
	 * @return team or null if not found
	 */
	Team getTeamByName(String teamName);

	/**
	 * Deletes a team.
	 *
	 * @param team team to delete
	 * @return true if deletion succeeded
	 */
	boolean deleteTeam(Team team);
}
