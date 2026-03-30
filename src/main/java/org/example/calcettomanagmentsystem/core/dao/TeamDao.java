package org.example.calcettomanagmentsystem.core.dao;

import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.List;

/**
 * DAO interface specializing in team-related operations.
 * <p>
 * This extends {@link GeneralDao} to include domain-specific logic for managing
 * team rosters and tournament associations.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public interface TeamDao extends GeneralDao<Team> {
    /**
     * Persists the addition of a player to a specific team's roster in the data store.
     * <p>
     * This method updates the team membership, enabling roster management and 
     * match participation tracking.
     * </p>
     *
     * @param team The target {@link Team} receiving the player.
     * @param player The {@link Player} to add to the team.
     * @return The updated {@link Team} instance reflecting the new roster.
     */
    Team addPlayer(Team team, Player player);

    /**
     * Retrieves all teams participating in a specific tournament from the data store.
     *
     * @param tournament The tournament context for filtering teams.
     * @return A {@link List} of all {@link Team} entities associated with the tournament.
     */
    List<Team> findByTournament(Tournament tournament);
}
