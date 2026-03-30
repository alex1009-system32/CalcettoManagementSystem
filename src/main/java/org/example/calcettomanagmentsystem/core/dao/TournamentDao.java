package org.example.calcettomanagmentsystem.core.dao;

import org.example.calcettomanagmentsystem.core.model.Tournament;

/**
 * DAO interface specializing in tournament-related operations.
 * <p>
 * This extends {@link GeneralDao} to include logic for controlling
 * the progression of tournament rounds.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public interface TournamentDao extends GeneralDao<Tournament> {
    /**
     * Increments the current round counter of the specified tournament in the data store.
     * <p>
     * This method advances the tournament's progression, marking the start 
     * of the next scheduled phase.
     * </p>
     *
     * @param tournament The {@link Tournament} whose current round should be advanced.
     * @return The updated {@link Tournament} instance with the incremented round number.
     */
    Tournament increaseRound(Tournament tournament);
}
