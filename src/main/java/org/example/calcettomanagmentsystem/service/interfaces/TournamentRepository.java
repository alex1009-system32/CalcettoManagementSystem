package org.example.calcettomanagmentsystem.service.interfaces;

import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.Optional;

/**
 * Repository interface specializing in tournament-related data operations.
 * <p>
 * This extends {@link Repository} to include methods for managing 
 * tournament-specific lifecycle events like round progression.
 * </p>
 *
 * @author Senior Developer
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
