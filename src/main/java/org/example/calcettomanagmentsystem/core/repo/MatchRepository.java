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
 * @version 0.1
 * @since 1.0
 */
public interface MatchRepository extends Repository<Match> {
    /**
     * Associates a team with a specific match in the repository.
     * <p>
     * This method acts as a mediator to register a team for a particular match, 
     * ensuring that the participation state is correctly persisted.
     * </p>
     *
     * @param team The {@link Team} to register for the match.
     * @param match The {@link Match} that the team is joining.
     * @return The updated {@link Match} instance including the new team assignment.
     */
    Match addTeam(Team team, Match match);

    /**
     * Records points for a team in a specific match.
     * <p>
     * This method persists the match outcome for a specific participant, 
     * which is critical for tournament rankings and round progression.
     * </p>
     *
     * @param team The {@link Team} whose score is being recorded.
     * @param match The {@link Match} in which the points were earned.
     * @param points The numeric score value to assign.
     * @return The updated {@link Match} instance with the new score data.
     */
    Match addPoints(Team team, Match match, int points);

    /**
     * Retrieves all matches associated with a specific tournament.
     *
     * @param tournament The {@link Tournament} context for filtering.
     * @return A {@link List} of all matches belonging to the specified tournament.
     */
    List<Match> findMatchesByTournament(Tournament tournament);

    /**
     * Retrieves matches for a specific tournament and round.
     * <p>
     * This provides a more granular way to fetch matches, typically used for 
     * displaying round-specific results in the UI.
     * </p>
     *
     * @param tournament The {@link Tournament} context for filtering.
     * @param round The round number to filter by.
     * @return A {@link List} of matches that match both the tournament and round criteria.
     */
    List<Match> findMatchesByTournament(Tournament tournament, int round);
}
