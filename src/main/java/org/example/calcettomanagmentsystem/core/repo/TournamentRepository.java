package org.example.calcettomanagmentsystem.core.repo;

import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.Optional;

/**
 * Repository interface specializing in tournament-related data operations.
 * <p>
 * This extends {@link Repository} to include methods for managing
 * tournament-specific lifecycle events like round progression.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public interface TournamentRepository extends Repository<Tournament> {
    /**
     * Increments the current round counter of a tournament in the repository.
     * <p>
     * This method persists the progression of the tournament to the next round, 
     * typically after all matches of the current round are completed.
     * </p>
     *
     * @param tournament The {@link Tournament} to advance to the next round.
     * @return An {@link Optional} containing the updated {@link Tournament} instance, 
     *         or empty if the operation failed.
     */
    Optional<Tournament> increaseRound(Tournament tournament);
}
