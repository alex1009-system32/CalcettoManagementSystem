package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class WinnerExtractor {
    /**
     * Ermittelt Siegerteams der Vorrunden basierend auf kumulierten Punkten.
     *
     * @param tournament Turnierkontext für die Vorrundenbewertung
     * @return sortierte Siegerliste
     * @implNote Die Punkte werden pro Team aggregiert und absteigend sortiert.
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
                                           .collect(Collectors.toMap(Map.Entry::getKey,
                                                                     Map.Entry::getValue,
                                                                     (e1, e2) -> e1,
                                                                     LinkedHashMap::new));

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

    public List<Team> getAllWinners(List<Match> matches) {
        List<Team> list = new LinkedList<>();
        Map<Team, Double> teams = matches.stream()
                                         .map(match -> match.teamResults()
                                                            .entrySet()
                                                            .stream()
                                                            .max(Map.Entry.comparingByValue()))
                                         .flatMap(Optional::stream)
                                         .collect(Collectors.toMap(Map.Entry::getKey,
                                                                   Map.Entry::getValue,
                                                                   Double::max));

        for (Team team : orderByPoints(teams).keySet()) {
            list.add(team);
        }

        return list;

    }

    public List<Team> getAllWinnersAfterPreRounds(List<Match> matches) {
        List<Team> list = new LinkedList<>();
        Map<Team, Double> teams = new HashMap<>();
        for (Match match : matches) {
            for(Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
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

    public Map<Team, Double> orderByPoints(Map<Team, Double> teams) {
        return teams.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Team, Double>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey,
                                              Map.Entry::getValue,
                                              (e1, e2) -> e1,
                                              LinkedHashMap::new));
    }
}
