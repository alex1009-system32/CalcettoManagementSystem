package org.example.calcettomanagmentsystem.core.dao;

import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * DAO interface specializing in player-related operations.
 * <p>
 * This extends {@link GeneralDao} to include methods for retrieving players
 * based on their tournament participation.
 * </p>
 *
 * @author Senior Developer
 */
public interface PlayerDao extends GeneralDao<Player> {
    /**
     * Retrieves all players registered for a specific tournament.
     *
     * @param tournament The tournament context.
     * @return A list of players belonging to the tournament.
     */
    List<Player> getAllPlayersFromTournament(@NotNull Tournament tournament);
}
