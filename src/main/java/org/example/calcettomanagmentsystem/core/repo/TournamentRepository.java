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
 * @version 0.0
 */
public interface TournamentRepository extends Repository<Tournament> {
    /**
     * Increments the current round number of a tournament.
     *
     * @param tournament The tournament to update.
     * @return An {@link Optional} containing the updated {@link Tournament} instance.
     */
    Optional<Tournament> increaseRound(Tournament tournament);
}
