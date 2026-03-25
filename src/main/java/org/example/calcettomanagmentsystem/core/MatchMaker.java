package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * Core logic class responsible for generating tournament match schedules.
 * <p>
 * This class handles both round-robin style preliminary rounds and 
 * bracket-style elimination rounds based on winners from previous stages.
 * </p>
 *
 * @author Senior Developer
 */
public class MatchMaker {
    /** Helper for shuffling team lists. */
    TeamShuffler teamShuffler;
    /** Helper for extracting winners from match results. */
    WinnerExtractor winnerExtractor;

    /**
     * Constructs a new MatchMaker with required helper components.
     *
     * @param teamShuffler Component for team randomization.
     * @param winnerExtractor Component for result analysis and winner identification.
     */
    public MatchMaker(TeamShuffler teamShuffler, WinnerExtractor winnerExtractor) {
        this.teamShuffler = teamShuffler;
        this.winnerExtractor = winnerExtractor;
    }

    /**
     * Generates preliminary rounds for a tournament using a rotation-based schedule.
     *
     * @param tournament The tournament context.
     * @param teams The list of participating teams.
     * @return A list of generated matches for all preliminary rounds.
     */
    public List<Match> makePreRounds(@NotNull Tournament tournament, List<Team> teams) {
        List<Team> rotatingTeams = new ArrayList<>(teams);

        List<Match> allPreRoundMatches = new ArrayList<>();
        int maxRounds = tournament.preRound();

        for (int round = 1; round <= maxRounds; round++) {
            List<Match> matchesForThisRound = generateRoundWithoutDummies(rotatingTeams, round, tournament);

            allPreRoundMatches.addAll(matchesForThisRound);
            if (rotatingTeams.size() > 1) {
                Collections.rotate(rotatingTeams.subList(1, rotatingTeams.size()), 1);
            }
        }
        return allPreRoundMatches;
    }

    /**
     * Creates matches for the next round following the completion of preliminary rounds.
     *
     * @param tournament The tournament context.
     * @param matches The preliminary round matches to extract winners from.
     * @return A list of new elimination matches.
     */
    public List<Match> makeMatchesForRoundAfterPreRounds(Tournament tournament, List<Match> matches) {
        List<Match> newMatches = new ArrayList<>();
        List<Team> winners = winnerExtractor.getAllWinnersAfterPreRounds(matches);

        int teamCount = getTotalTeamNumbers(winners);

        for (int i = 0; i < teamCount / 2; i++) {
            Match match = new Match(tournament.currentRound() + 1, tournament);
            match.teamResults().put(winners.get(i), -1.0);
            match.teamResults().put(winners.get(winners.size() - 1 - i), -1.0);
            newMatches.add(match);
        }
        return newMatches;
    }

    /**
     * Creates elimination matches for the next round based on winners of the current round.
     *
     * @param tournament The tournament context.
     * @param matches The current round matches.
     * @return A list of matches for the subsequent round.
     */
    public List<Match> makeMatchesForRound(Tournament tournament, List<Match> matches) {
        List<Match> newMatches = new ArrayList<>();
        List<Team> winners = winnerExtractor.getAllWinners(matches);

        for (int i = 0; i < winners.size() / 2; i++) {
            Match match = new Match(tournament.currentRound() + 1, tournament);
            match.teamResults().put(winners.get(i), -1.0);
            match.teamResults().put(winners.get(winners.size() - 1 - i), -1.0);
            newMatches.add(match);
        }
        return newMatches;
    }

    /**
     * Generates a single round of matches using a round-robin rotation algorithm.
     *
     * @param teams The list of teams to schedule.
     * @param roundOffset The round number being generated.
     * @param tournament The tournament context.
     * @return A list of matches for the round.
     */
    private @NotNull List<Match> generateRoundWithoutDummies(@NotNull List<Team> teams,
                                                             int roundOffset,
                                                             Tournament tournament) {
        List<Match> matches = new ArrayList<>();
        int n = teams.size();

        List<Team> rotationList = new ArrayList<>(teams);

        int effectiveN = (n % 2 == 0) ? n : n + 1;

        for (int i = 0; i < effectiveN / 2; i++) {
            int homeIdx = i;
            int awayIdx = effectiveN - 1 - i;

            if (homeIdx < n && awayIdx < n) {
                Team t1 = rotationList.get(homeIdx);
                Team t2 = rotationList.get(awayIdx);

                Map<Team, Double> results = new HashMap<>();
                results.put(t1, 0.0);
                results.put(t2, 0.0);

                matches.add(new Match(-1, roundOffset, tournament, results));
            }
        }
        return matches;
    }

    /**
     * Calculates the target number of teams for elimination rounds (power of 2).
     *
     * @param teams The list of candidate teams.
     * @return The highest power of 2 that is less than or equal to the team count.
     */
    private int getTotalTeamNumbers(List<Team> teams) {
        boolean n = true;
        int i;
        for (i = 0; i < teams.size() && n; i++) {
            if (teams.size() > Math.pow(2, i) && teams.size() <= Math.pow(2, i + 1)) n = false;
        }
        return (int) Math.pow(2, i);
    }
}
