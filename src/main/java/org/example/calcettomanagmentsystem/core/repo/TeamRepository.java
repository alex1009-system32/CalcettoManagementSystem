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
 * @version 0.0
 */
public interface TeamRepository extends Repository<Team> {
    /**
     * Adds a player to a specific team's roster.
     *
     * @param player The player to add.
     * @param team The target team.
     * @return The updated {@link Team} instance.
     */
    Team addPlayer(Player player, Team team);

    /**
     * Retrieves all teams participating in a specific tournament.
     *
     * @param tournament The tournament context.
     * @return A list of teams in the tournament.
     */
    List<Team> findByTournament(Tournament tournament);
}
