package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

/**
 * Data access contract for player management.
 */
public interface PlayerDao {
	/**
	 * Creates and persists a new player in the context of a tournament.
	 *
	 * @param pname player name
	 * @param pemail player email
	 * @param tournament associated tournament
	 * @return created player
	 */
	Player addPlayer(String pname, String pemail, Tournament tournament);

	/**
	 * Loads all players in the system.
	 *
	 * @return list of players
	 */
	List<Player> getAllPlayers();

	/**
	 * Loads all players belonging to a tournament.
	 *
	 * @param tournament filter tournament
	 * @return list of players
	 */
	List<Player> getAllPlayersFromTournament(Tournament tournament);

	/**
	 * Finds a player by id.
	 *
	 * @param pid player id
	 * @return player or null if not found
	 */
	Player getPlayerById(int pid);

	/**
	 * Deletes a player.
	 *
	 * @param player player to delete
	 * @return true if deletion succeeded
	 */
	boolean deletePlayer(Player player);
}
