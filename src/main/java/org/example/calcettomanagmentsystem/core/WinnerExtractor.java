package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
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
    @NotNull
    public List<Team> getWinnersOfCurrentPreRound(@NotNull Tournament tournament, MatchDao matchDao) {
        List<Team> winner = new ArrayList<>();
        Map<Team, Double> teams = new HashMap<>();

        for (int i = 0; i < tournament.preRound(); i++) {
            List<Match> matches = matchDao.findMatchesByTournament(tournament);
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
                if (counter > sortedMap.size()/2) break sortingOut;
                counter++;
                winner.add(entry.getKey());
            }
        }

        return winner;
    }

    /**
     * Ermittelt Siegerteams der aktuellen Runde basierend auf Match-Punkten.
     *
     * @param tournament Turnierkontext für die Rundenbewertung
     * @return Siegerliste der aktuellen Runde
     */
    @NotNull
    public List<Team> getWinnersOfCurrentRound(Tournament tournament, MatchDao matchDao) {
        List<Match> matches = matchDao.findMatchesByTournament(tournament);
        List<Team> winners = new ArrayList<>();

        for (Match match : matches) {
            Team winner = null;
            for (Map.Entry<Team, Double> entry : match.teamResults().entrySet()) {
                if (winner == null) {
                    winner = entry.getKey();
                } else if (entry.getValue() > match.teamResults().get(winner)) {
                    winner = entry.getKey();
                }
            }
            winners.add(winner);
        }

        return winners;
    }
}
