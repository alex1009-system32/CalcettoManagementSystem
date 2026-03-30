package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.core.repo.impl.PlayerRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Service for managing player records and tournament associations.
 * <p>
 * This class provides methods for creating players with validation, 
 * deleting records, and filtering players by tournament.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class PlayerService {
    /** The repository responsible for persisting and retrieving player data. */
    private final PlayerRepository playerRepository;

    /**
     * Constructs a new PlayerService with the specified repository for player data access.
     *
     * @param playerRepository The repository used for all player-related business logic.
     */
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Creates and persists a new player with the given details.
     *
     * @param name The display name of the player.
     * @param email The contact email of the player.
     * @param tournament The tournament context.
     * @return The persisted {@link Player} instance, or {@code null} if saving failed.
     * @throws ValidationException If name or email format is invalid.
     */
    public Player create(@NotNull String name, @NotNull String email, @NotNull Tournament tournament) throws ValidationException {
        if (name.isEmpty()) throw new ValidationException("Player name cannot be empty.");
        if (name.matches(".* .*")) throw new ValidationException("Player name cannot contain spaces.");
        if (name.matches(".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~].*"))
            throw new ValidationException("Player name cannot contain special characters.");

        if (email.isEmpty()) throw new ValidationException("Email address cannot be empty.");
        if (email.matches(".* .*")) throw new ValidationException("Email address cannot contain spaces.");
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            throw new ValidationException("Invalid email format.");

        return playerRepository.save(new Player(name, email, tournament)).orElse(null);
    }

    /**
     * Deletes a player record from the database.
     *
     * @param player The player to delete.
     * @return {@code true} if deletion was successful; {@code false} otherwise.
     * @throws ValidationException If the player ID is invalid.
     */
    public boolean delete(@NotNull Player player) throws ValidationException {
        if (player.id() < 0) throw new ValidationException("Invalid player ID.");

        return playerRepository.delete(player);
    }

    /**
     * Retrieves all players from the database.
     *
     * @return A list of all players.
     */
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    /**
     * Retrieves all players registered for a specific tournament.
     *
     * @param tournament The tournament context.
     * @return A list of players associated with the tournament.
     * @throws ValidationException If the tournament ID is invalid.
     */
    public List<Player> findAllByTournament(Tournament tournament) throws ValidationException {
        if (tournament.id() < 0) throw new ValidationException("Invalid tournament ID.");

        return playerRepository.findAll()
                .stream()
                .filter(player -> player.tournament().id() == tournament.id())
                .toList();
    }
}
