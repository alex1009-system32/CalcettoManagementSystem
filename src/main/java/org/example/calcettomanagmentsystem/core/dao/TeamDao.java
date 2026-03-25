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
 * @author Senior Developer
 */
public interface TeamDao extends GeneralDao<Team> {
    /**
     * Adds a player to a specific team's roster.
     *
     * @param team The target {@link Team}.
     * @param player The {@link Player} to add.
     * @return The updated {@link Team} instance.
     */
    Team addPlayer(Team team, Player player);

    /**
     * Retrieves all teams participating in a specific tournament.
     *
     * @param tournament The tournament context.
     * @return A list of teams in the tournament.
     */
    List<Team> findByTournament(Tournament tournament);
}
