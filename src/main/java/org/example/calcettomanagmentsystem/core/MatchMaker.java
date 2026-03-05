package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

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

    public boolean makePreRounds(@NotNull Tournament tournament,
                                 TournamentDao tournamentDao,
                                 MatchDao matchDao,
                                 TeamDao teamDao) {
        if (tournament.currentRound() != 0) {
            return false;
        }

        List<Team> teams = teamDao.getTeams(tournament);
        List<List<List<Team>>> allTeamLists = new ArrayList<>();

        for (int i = 0; i < tournament.preRound(); i++) {

            List<List<Team>> teamList = teamShuffler.shuffleTeamList(teams, tournament.maxTeamSize());
            allTeamLists.add(teamList);

            if (i == 0) {
                createMatches(teamList, tournament, tournamentDao, matchDao);
            } else {
                if (hasSameTeam(allTeamLists)) {
                    i--;
                    allTeamLists.removeLast();
                } else {
                    createMatches(teamList, tournament, tournamentDao, matchDao);
                }
            }
        }

        return true;
    }

    /**
     * Erzeugt Matches für die aktuelle Runde basierend auf dem Turnierstatus.
     *
     * @param tournament Turnierkontext zur Ermittlung der Gewinner
     * @implNote Es wird ein Spiegel-Pairing erzeugt, um starke und schwächere
     * Teams zu mischen.
     */
    public boolean makeMatchesForRound(@NotNull Tournament tournament,
                                       TournamentDao tournamentDao,
                                       MatchDao matchDao) {
        List<Team> teams;

        if (tournament.currentRound() == tournament.preRound()) {
            teams = winnerExtractor.getWinnersOfCurrentPreRound(tournament, matchDao);
        } else {
            teams = winnerExtractor.getWinnersOfCurrentRound(tournament, matchDao);
        }

        List<List<Team>> newTeams = IntStream.range(0, teams.size() / 2)
                                             .mapToObj(i -> Arrays.asList(teams.get(i),
                                                                          teams.get(teams.size() - 1 - i)))
                                             .collect(Collectors.toList());

        createMatches(newTeams, tournament, tournamentDao, matchDao);
        return true;
    }

    /**
     * Persistiert Matches für vorbereitete Team-Paarungen.
     *
     * @param teams      Team-Paare, optional mit Einzelteam für Freilos
     * @param tournament Turnierkontext für Match-Erstellung
     */
    private void createMatches(@NotNull List<List<Team>> teams,
                               Tournament tournament,
                               TournamentDao tournamentDao,
                               MatchDao matchDao) {

        if (teams.isEmpty()) return;

        Match match;
        for (List<Team> teamList : teams) {
            match = matchDao.save(new Match(tournament.currentRound(), tournament))
                            .orElseThrow(() -> new DataAccessException("Couldn't find match"));
            for (Team team : teamList) {
                matchDao.registerTeam(team, match);
            }
        }

        tournamentDao.increaseRound(tournament);
    }




    /**
     * Prüft, ob eine Paarungskombination bereits verwendet wurde.
     *
     * @param allTeamLists Historie der Paarungen
     * @return {@code true}, wenn eine Doppelung erkannt wird
     */
    private boolean hasSameTeam(@NotNull List<List<List<Team>>> allTeamLists) {
        if (allTeamLists.size() < 2) return false;

        for (int i = 0; i < allTeamLists.size(); i++) {
            for (int j = i + 1; j < allTeamLists.size(); j++) {
                if (compareTwo(allTeamLists.get(i), allTeamLists.get(j))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Vergleicht zwei Paarungslisten auf identische Team-Kombinationen.
     *
     * @param listA erste Paarungsliste
     * @param listB zweite Paarungsliste
     * @return {@code true}, wenn identische Paarungen vorhanden sind
     */
    private boolean compareTwo(@NotNull List<List<Team>> listA, List<List<Team>> listB) {
        for (List<Team> teamList1 : listA) {
            for (List<Team> teamList2 : listB) {
                if (teamList1.contains(teamList2.getFirst()) && teamList1.contains(teamList2.getLast())) {
                    return true;
                }
            }
        }

        return false;
    }

}
