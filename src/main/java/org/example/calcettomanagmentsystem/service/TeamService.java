package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.shared.exceptions.ValidationException;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.service.repo.TeamRepository;

import java.util.List;

/**
 * Service for managing team records and player assignments.
 * <p>
 * This class provides methods for creating teams and assigning players
 * to teams within a tournament context.
 * </p>
 *
 * @author Senior Developer
 */
public class TeamService {
    /** The repository handling team data persistence. */
    TeamRepository teamRepository;

    /**
     * Constructs a new TeamService with the specified repository.
     *
     * @param teamRepository The repository for team data operations.
     */
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Saves a new team with the specified name.
     *
     * @param name The name of the team.
     * @return The persisted {@link Team} instance.
     * @throws ValidationException If saving fails.
     */
    public Team save(String name) {
        return teamRepository.save(new Team(name))
                .orElseThrow(() -> new ValidationException("Failed to save team to database."));
    }

    /**
     * Assigns a player to a specific team.
     *
     * @param player The {@link Player} to assign.
     * @param team The target {@link Team}.
     * @return The updated {@link Team} instance.
     */
    public Team save(Player player, Team team) {
        return teamRepository.addPlayer(player, team);
    }

    /**
     * Retrieves all teams from the database.
     *
     * @return A list of all teams.
     */
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    /**
     * Retrieves all teams participating in a specific tournament.
     *
     * @param tournament The tournament context.
     * @return A list of teams in the tournament.
     * @throws ValidationException If the tournament ID is invalid.
     */
    public List<Team> findAllByTournament(Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("Invalid tournament ID.");

        return teamRepository.findByTournament(tournament);
    }
}
