package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Orchestriert die Match-Erstellung für Turniere.
 * <p>
 * Die Klasse bündelt Turnierlogik und Persistenzzugriffe, um eine konsistente
 * Match-Historie über Vorrunden und Hauptrunden zu gewährleisten.
 * </p>
 *
 */
public class MatchMaker {
    TeamShuffler teamShuffler;
    WinnerExtractor winnerExtractor;

    public MatchMaker(TeamShuffler teamShuffler, WinnerExtractor winnerExtractor) {
        this.teamShuffler = teamShuffler;
        this.winnerExtractor = winnerExtractor;
    }

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

    private int getTotalTeamNumbers(List<Team> teams) {
        boolean n = true;
        int i;
        for (i = 0; i < teams.size() && n; i++) {
            if (teams.size() > Math.pow(2, i) && teams.size() <= Math.pow(2, i + 1)) n = false;
        }
        return (int) Math.pow(2, i);
    }
}
