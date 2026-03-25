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
 * @author Senior Developer
 */
public interface MatchDao extends GeneralDao<Match> {
    /**
     * Associates a team with a specific match.
     *
     * @param team The {@link Team} to register.
     * @param match The {@link Match} to register the team for.
     * @return The updated {@link Match} instance.
     */
    Match registerTeam(Team team, Match match);

    /**
     * Updates the points/score for a team in a specific match.
     *
     * @param team The {@link Team} whose points are being assigned.
     * @param point The score value to assign.
     * @param match The {@link Match} where the points were earned.
     * @return The updated {@link Match} instance.
     */
    Match assignPoints(Team team, double point, Match match);

    /**
     * Retrieves all matches associated with a specific tournament.
     *
     * @param tournament The tournament context.
     * @return A list of matches belonging to the tournament.
     */
    List<Match> findMatchesByTournament(@NotNull Tournament tournament);

    /**
     * Retrieves all matches for a specific tournament and round.
     *
     * @param tournament The tournament context.
     * @param round The round number.
     * @return A filtered list of matches.
     */
    List<Match> findMatchesByTournament(@NotNull Tournament tournament, int round);
}

