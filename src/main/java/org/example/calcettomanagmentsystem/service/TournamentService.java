package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.shared.exceptions.DataAccessException;
import org.example.calcettomanagmentsystem.shared.exceptions.ValidationException;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.MakerRepository;
import org.example.calcettomanagmentsystem.service.repo.TournamentRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Service for managing tournament lifecycles and configuration.
 * <p>
 * This class provides methods for creating tournaments, 
 * advancing rounds, and deleting tournament records.
 * </p>
 *
 * @author Senior Developer
 */
public class TournamentService {
    /** The repository handling tournament data persistence. */
    TournamentRepository tournamentRepository;
    /** The repository handling tournament scheduling logic. */
    MakerRepository makerRepository;

    /**
     * Constructs a new TournamentService with required repositories.
     *
     * @param tournamentRepository Repository for tournament data.
     * @param makerRepository Repository for tournament generation logic.
     */
    public TournamentService(TournamentRepository tournamentRepository, MakerRepository makerRepository) {
        this.tournamentRepository = tournamentRepository;
        this.makerRepository = makerRepository;
    }

    /**
     * Creates and persists a new tournament with the specified parameters.
     *
     * @param name The name of the tournament.
     * @param duration The duration of the tournament in days.
     * @param preRound The number of preliminary rounds.
     * @param maxTeamSize The maximum number of players allowed per team.
     * @return The persisted {@link Tournament} instance.
     * @throws ValidationException If name is empty, contains invalid characters, or team size is too small.
     * @throws DataAccessException If saving to the database fails.
     */
    public Tournament create(@NotNull String name, long duration, int preRound, int maxTeamSize) {
        if (name == null || name.isBlank()) throw new ValidationException("Tournament name cannot be empty.");
        if (!name.matches("^[a-zA-Z0-9]*$")) throw new ValidationException("Tournament name contains invalid characters.");
        if (maxTeamSize < 1) throw new ValidationException("A team must have at least one player.");

        return tournamentRepository.save(new Tournament(name, duration, preRound, maxTeamSize))
                .orElseThrow(() -> new DataAccessException("Could not save tournament."));
    }

    /**
     * Advances the current round of a tournament.
     *
     * @param tournament The tournament to advance.
     * @return The updated {@link Tournament} instance.
     * @throws ValidationException If the tournament ID is invalid.
     * @throws DataAccessException If the tournament cannot be found or updated.
     */
    public Tournament increaseRound(Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("Invalid tournament ID.");

        return tournamentRepository.increaseRound(tournament)
                .orElseThrow(() -> new DataAccessException("Could not find tournament to update."));
    }

    /**
     * Deletes a tournament record from the database.
     *
     * @param tournament The tournament to delete.
     * @return {@code true} if deletion was successful; {@code false} otherwise.
     * @throws ValidationException If the tournament ID is invalid.
     */
    public boolean delete(@NotNull Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("Invalid tournament ID.");

        return tournamentRepository.delete(tournament);
    }

    /**
     * Retrieves all tournament records from the database.
     *
     * @return A list of all tournaments.
     */
    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }
}
