package org.example.calcettomanagmentsystem.core.dao;

import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * DAO interface specializing in match-related operations.
 * <p>
 * This extends {@link GeneralDao} to include domain-specific logic for managing
 * team assignments and scoring within matches.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public interface MatchDao extends GeneralDao<Match> {
    /**
     * Associates a team with a specific match in the data store.
     * <p>
     * This method persists the relationship between a team and a match, 
     * which is necessary for tracking participants.
     * </p>
     *
     * @param team The {@link Team} to register for the match.
     * @param match The {@link Match} to which the team should be added.
     * @return The updated {@link Match} instance with the newly registered team.
     */
    Match registerTeam(Team team, Match match);

    /**
     * Updates the points/score for a team in a specific match.
     * <p>
     * This method persists the match outcome for a specific team, allowing for 
     * result tracking and winner identification.
     * </p>
     *
     * @param team The {@link Team} whose points are being assigned.
     * @param point The numeric score value to assign to the team.
     * @param match The {@link Match} where the points were earned.
     * @return The updated {@link Match} instance reflecting the new score.
     */
    Match assignPoints(Team team, double point, Match match);

    /**
     * Retrieves all matches associated with a specific tournament from the data store.
     *
     * @param tournament The tournament context for filtering matches.
     * @return A {@link List} of matches belonging to the specified tournament.
     */
    List<Match> findMatchesByTournament(@NotNull Tournament tournament);

    /**
     * Retrieves all matches for a specific tournament and round.
     * <p>
     * Useful for fetching round-specific results or scheduling details.
     * </p>
     *
     * @param tournament The tournament context for filtering.
     * @param round The round number to filter by.
     * @return A {@link List} of matches that match both the tournament and round criteria.
     */
    List<Match> findMatchesByTournament(@NotNull Tournament tournament, int round);
}

