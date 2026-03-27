package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.core.repo.impl.MatchRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Service for managing match operations and result processing.
 * <p>
 * This class provides a high-level API for persisting matches, 
 * assigning teams, and updating scores with integrated validation logic.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class MatchService {
    /** The repository handling match data persistence. */
    MatchRepository matchRepository;

    /**
     * Constructs a new MatchService with the specified repository.
     *
     * @param matchRepository The repository for match data operations.
     */
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    /**
     * Saves a new match record for a specific round and tournament.
     *
     * @param round The round number.
     * @param tournament The associated tournament.
     * @return The persisted {@link Match} instance.
     * @throws ValidationException If round or tournament ID is invalid.
     */
    public Match save(int round, Tournament tournament) throws ValidationException {
        if (round < 0) throw new ValidationException("Match round must be greater than 0.");

        if (tournament.id() < 0) throw new ValidationException("Tournament ID must be greater than 0.");

        return matchRepository.save(new Match(round, tournament))
                              .orElseThrow(() -> new ValidationException("Failed to save match to database."));
    }

    /**
     * Adds a team to an existing match.
     *
     * @param team The team to add.
     * @param match The match to update.
     * @return The updated {@link Match} instance.
     * @throws ValidationException If IDs are invalid or team is already in the match.
     */
    public Match addTeam(Team team, Match match) throws ValidationException {
        if (team.id() < 0) throw new ValidationException("Team ID must be greater than 0.");
        if (match.id() < 0) throw new ValidationException("Match ID must be greater than 0.");

        if (match.teamResults().keySet().stream().anyMatch(team1 -> team1.id() == team.id()))
            throw new ValidationException("Team already exists in this match.");

        return matchRepository.addTeam(team, match);
    }

    /**
     * Records points for a team in a specific match.
     *
     * @param team The team whose score is being set.
     * @param match The match where the score occurred.
     * @param points The number of points to assign.
     * @return The updated {@link Match} instance.
     * @throws ValidationException If points or IDs are invalid.
     */
    public Match setPoints(Team team, Match match, int points) throws ValidationException {
        if (points < 0) throw new ValidationException("Points must be non-negative.");
        if (team.id() < 0) throw new ValidationException("Team ID must be greater than 0.");
        if (match.id() < 0) throw new ValidationException("Match ID must be greater than 0.");

        return matchRepository.addPoints(team, match, points);
    }

    /**
     * Retrieves all match records from the database.
     *
     * @return A list of all matches.
     */
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    /**
     * Finds all matches belonging to a specific tournament.
     *
     * @param tournament The tournament context.
     * @return A list of matches for the tournament.
     */
    public List<Match> findMatchesByTournament(@NotNull Tournament tournament) {
        return matchRepository.findMatchesByTournament(tournament);
    }

    /**
     * Finds all matches for the current round of a tournament.
     *
     * @param tournament The tournament context.
     * @return A list of matches in the current round.
     */
    public List<Match> findMatchesByTournamentInCurrentRound(@NotNull Tournament tournament) {
        return matchRepository.findMatchesByTournament(tournament, tournament.currentRound());
    }
}
