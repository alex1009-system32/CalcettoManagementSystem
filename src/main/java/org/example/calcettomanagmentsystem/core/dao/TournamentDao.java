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
 * @version 0.0
 */
public interface TournamentDao extends GeneralDao<Tournament> {
    /**
     * Increments the current round of the specified tournament in the database.
     *
     * @param tournament The {@link Tournament} whose round is to be increased.
     * @return The updated {@link Tournament} instance.
     */
    Tournament increaseRound(Tournament tournament);
}
