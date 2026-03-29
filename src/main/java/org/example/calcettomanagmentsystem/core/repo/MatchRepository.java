package org.example.calcettomanagmentsystem.core.repo;

import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.List;

/**
 * Repository interface specializing in match-related data operations.
 * <p>
 * This extends {@link Repository} to include methods for managing team assignments,
 * scoring, and tournament-based filtering of matches.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public interface MatchRepository extends Repository<Match> {
    /**
     * Associates a team with a specific match.
     *
     * @param team The team to register.
     * @param match The match to update.
     * @return The updated {@link Match} instance.
     */
    Match addTeam(Team team, Match match);

    /**
     * Records points for a team in a specific match.
     *
     * @param team The team whose score is being assigned.
     * @param match The match context.
     * @param points The number of points earned.
     * @return The updated {@link Match} instance.
     */
    Match addPoints(Team team, Match match, int points);

    /**
     * Retrieves all matches associated with a specific tournament.
     *
     * @param tournament The tournament context.
     * @return A list of matches for the tournament.
     */
    List<Match> findMatchesByTournament(Tournament tournament);

    /**
     * Retrieves matches for a specific tournament and round.
     *
     * @param tournament The tournament context.
     * @param round The round number to filter by.
     * @return A list of matches in the specified round.
     */
    List<Match> findMatchesByTournament(Tournament tournament, int round);
}
