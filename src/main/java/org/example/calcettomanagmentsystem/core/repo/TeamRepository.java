package org.example.calcettomanagmentsystem.core.repo;

import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.List;

/**
 * Repository interface specializing in team-related data operations.
 * <p>
 * This extends {@link Repository} to include methods for managing team rosters
 * and tournament associations.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public interface TeamRepository extends Repository<Team> {
    /**
     * Persists the addition of a player to a specific team's roster in the repository.
     * <p>
     * This method mediates between the service layer and the DAO to update 
     * team membership.
     * </p>
     *
     * @param player The {@link Player} entity to add to the team.
     * @param team The target {@link Team} receiving the player.
     * @return The updated {@link Team} instance reflecting the roster change.
     */
    Team addPlayer(Player player, Team team);

    /**
     * Retrieves all teams participating in a specific tournament.
     *
     * @param tournament The {@link Tournament} context for filtering teams.
     * @return A {@link List} of all teams registered for the given tournament.
     */
    List<Team> findByTournament(Tournament tournament);
}
