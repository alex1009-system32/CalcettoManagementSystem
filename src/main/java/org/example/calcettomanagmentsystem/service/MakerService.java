package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.core.repo.MakerRepository;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Service responsible for high-level tournament scheduling and round generation.
 * <p>
 * This class orchestrates the creation of teams and the generation of matches 
 * for different tournament phases (preliminary, elimination).
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class MakerService {
    /** The repository handling the low-level generation logic. */
    MakerRepository makerRepository;

    /**
     * Constructs a new MakerService with the specified repository.
     *
     * @param makerRepository The repository used for generation operations.
     */
    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    /**
     * Automatically generates teams from the players registered in a tournament.
     *
     * @param tournament The tournament to generate teams for.
     * @return A list of newly created teams.
     */
    public List<Team> generateTeams(Tournament tournament) throws ValidationException {
        return makerRepository.generateTeams(ServiceManager.getPlayerService()
                                                           .findAllByTournament(tournament), tournament.maxTeamSize());
    }

    /**
     * Generates the next set of matches for a tournament based on its current state.
     *
     * @param tournament The tournament context.
     * @return A list of generated matches for the next round.
     * @throws ValidationException If the tournament state is invalid or previous matches are not completed.
     */
    public List<Match> generateNextMatches(@NotNull Tournament tournament) throws ValidationException {
        if (tournament.id() < 0) throw new ValidationException("Tournament ID must be greater than 0.");

        List<Match> matches;
        switch (tournament) {
            case Tournament t when t.currentRound() == 0 -> {
                matches = generatePreRoundMatches(tournament);
            }
            case Tournament t when t.preRound() == t.currentRound() -> {
                matches = generateRoundMatchesAfterPreRounds(tournament);
            }
            case Tournament t -> {
                matches = generateRoundMatches(tournament);
            }
        }

        return matches;
    }

    /**
     * Generates matches for the preliminary round phase.
     *
     * @param tournament The tournament context.
     * @return A list of generated matches.
     */
    private List<Match> generatePreRoundMatches(@NotNull Tournament tournament) throws ValidationException {
        List<Team> teams = ServiceManager.getTeamService().findAllByTournament(tournament);
        return makerRepository.generatePreRoundMatches(tournament, teams);
    }

    /**
     * Generates elimination matches immediately following the preliminary phase.
     *
     * @param tournament The tournament context.
     * @return A list of matches for the first elimination round.
     * @throws ValidationException If preliminary matches are not all completed.
     */
    private List<Match> generateRoundMatchesAfterPreRounds(@NotNull Tournament tournament) throws ValidationException {
        List<Match> matches = ServiceManager.getMatchService().findMatchesByTournament(tournament);

        if (!areAllRoundsCompleted(matches)) throw new ValidationException("Matches not finished.");

        return makerRepository.gerateRoundMatchesAfterPreRounds(tournament, matches);
    }

    /**
     * Generates elimination matches for subsequent rounds.
     *
     * @param tournament The tournament context.
     * @return A list of matches for the next round.
     * @throws ValidationException If current round matches are not all completed.
     */
    private List<Match> generateRoundMatches(@NotNull Tournament tournament) throws ValidationException {
        List<Match> matches = ServiceManager.getMatchService().findMatchesByTournamentInCurrentRound(tournament);

        if (!areAllRoundsCompleted(matches)) throw new ValidationException("Matches not finished.");

        return makerRepository.gerateRoundMatches(tournament, matches);
    }

    /**
     * Checks if all provided matches have recorded scores.
     *
     * @param matches The list of matches to verify.
     * @return {@code true} if all matches have scores (>= 0); {@code false} otherwise.
     */
    private boolean areAllRoundsCompleted(List<Match> matches) {
        for (Match match : matches) {
            for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
                if (entry.getValue() < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
