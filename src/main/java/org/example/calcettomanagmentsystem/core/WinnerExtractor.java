package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Component for analyzing match results and identifying winners.
 * <p>
 * This class handles aggregating points across matches and ranking teams
 * to determine who advances to the next rounds of the tournament.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class WinnerExtractor {

    /**
     * Identifies winners of the current preliminary rounds for a tournament.
     * <p>
     * <b>Note:</b> This method is scheduled for removal in future versions.
     * </p>
     *
     * @param tournament The tournament context for which winners are extracted.
     * @return A {@link List} of {@link Team} entities that have qualified to advance.
     * @deprecated Use {@link #getAllWinnersAfterPreRounds(List)} for more flexible analysis.
     */
    @Deprecated
    @NotNull
    public List<Team> getWinnersOfCurrentPreRound(@NotNull Tournament tournament) {
        List<Team> winner = new ArrayList<>();
        Map<Team, Double> teams = new HashMap<>();

        for (int i = 0; i < tournament.preRound(); i++) {
            List<Match> matches = ServiceManager.getMatchService().findMatchesByTournament(tournament);
            for (Match match : matches) {
                match.teamResults().forEach((key, value) -> {
                    if (teams.containsKey(key)) {
                        teams.replace(key, teams.get(key) + value);
                    } else {
                        teams.put(key, value);
                    }
                });
            }
        }

        Map<Team, Double> sortedMap = teams.entrySet()
                .stream()
                .sorted(Map.Entry.<Team, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        sortingOut:
        {
            int counter = 0;
            for (Map.Entry<Team, Double> entry : sortedMap.entrySet()) {
                if (counter > sortedMap.size() / 2) break sortingOut;
                counter++;
                winner.add(entry.getKey());
            }
        }

        return winner;
    }

    /**
     * Calculates total points and ranks teams after preliminary rounds.
     *
     * @param matches The list of preliminary round matches.
     * @return A list of teams ranked by total points earned across all provided matches.
     */
    public List<Team> getAllWinnersAfterPreRounds(List<Match> matches) {
        List<Team> list = new LinkedList<>();
        Map<Team, Double> teams = new HashMap<>();
        for (Match match : matches) {
            for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
                if (teams.containsKey(entry.getKey())) {
                    teams.put(entry.getKey(), teams.get(entry.getKey()) + entry.getValue());
                } else {
                    teams.put(entry.getKey(), entry.getValue());
                }
            }
        }

        for (Team team : orderByPoints(teams).keySet()) {
            list.add(team);
        }

        return list;
    }

    /**
     * Extracts winners from a provided list of matches.
     *
     * @param matches The list of matches to analyze.
     * @return A list of winning teams ordered by their total points.
     */
    public List<Team> getAllWinners(List<Match> matches) {
        List<Team> list = new LinkedList<>();
        Map<Team, Double> teams = matches.stream()
                .map(match -> match.teamResults().entrySet().stream().max(Map.Entry.comparingByValue()))
                .flatMap(Optional::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::max));

        for (Team team : orderByPoints(teams).keySet()) {
            list.add(team);
        }

        return list;
    }

    /**
     * Sorts a map of teams and their points in descending order.
     *
     * @param teams A map containing team-to-score associations.
     * @return A sorted {@link LinkedHashMap} of the teams.
     */
    public Map<Team, Double> orderByPoints(Map<Team, Double> teams) {
        return teams.entrySet()
                .stream()
                .sorted(Map.Entry.<Team, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
