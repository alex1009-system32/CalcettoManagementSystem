package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
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
 * @see org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao
 * @see org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao
 */
public class MatchMaker {

    /**
     * Erzeugt Vorrunden, sofern das Turnier noch nicht gestartet wurde.
     *
     * @param tournament Turnierkontext für Paarungen und Persistenz
     * @implNote Die Paarungen werden zufällig erzeugt; die Strategie ist auf
     * Wiederholung angewiesen, um Dopplungen zu vermeiden.
     */
    public void makePreRounds(@NotNull Tournament tournament) {
        if (tournament.getCurrendRound() != 0) {
            return;
        }

        SQLiteTournamentDao sqliteTournamentDao = new SQLiteTournamentDao();
        SQLiteMatchDao sqliteMatchDao = new SQLiteMatchDao();

        List<Team> teams = new SQLiteTeamDao().getAllTeamsFromTournament(tournament);
        List<List<List<Team>>> allTeamLists = new ArrayList<>();

        for (int i = 0; i < tournament.getPreRound(); i++) {

            List<List<Team>> teamList = shuffleTeamList(teams);
            allTeamLists.add(teamList);

            if (i == 0) {

                createMatches(teamList, tournament);

            } else {

                if (hasSameTeam(allTeamLists)) {

                    i--;
                    allTeamLists.removeLast();

                } else {

                    createMatches(teamList, tournament);

                }

            }

        }

        // Updates the tournaments current round to the Preround.
        for (int i = tournament.getCurrendRound(); i <= tournament.getPreRound(); i++)
            sqliteTournamentDao.increaseRound(tournament);


    }

    /**
     * Erzeugt Matches für die aktuelle Runde basierend auf dem Turnierstatus.
     *
     * @param tournament Turnierkontext zur Ermittlung der Gewinner
     * @implNote Es wird ein Spiegel-Pairing erzeugt, um starke und schwächere
     * Teams zu mischen.
     */
    public void makeMatchesForRound(@NotNull Tournament tournament) {

        List<Team> teams;
        SQLiteMatchDao sqliteMatchDao = new SQLiteMatchDao();
        Match match;

        if (tournament.getCurrendRound() == tournament.getPreRound()) {
            teams = getTheWinnersOfCurrentPreRound(tournament);
        } else {
            teams = getTheWinnersOfCurrentRound(tournament);
        }

        List<List<Team>> newTeams = IntStream.range(0, teams.size() / 2)
                                             .mapToObj(i -> Arrays.asList(teams.get(i),
                                                                          teams.get(teams.size() - 1 - i)))
                                             .collect(Collectors.toList());

        createMatches(newTeams, tournament);
        new SQLiteTournamentDao().increaseRound(tournament);

    }

    /**
     * Persistiert Matches für vorbereitete Team-Paarungen.
     *
     * @param teams      Team-Paare, optional mit Einzelteam für Freilos
     * @param tournament Turnierkontext für Match-Erstellung
     */
    private void createMatches(@NotNull List<List<Team>> teams, Tournament tournament) {
        if (teams.size() == 0) return;

        SQLiteMatchDao sqliteMatchDao = new SQLiteMatchDao();
        Match match;

        for (List<Team> teamList : teams) {
            match = sqliteMatchDao.addMatch(tournament);

            for (Team team : teamList) {
                sqliteMatchDao.addTeamToMatch(team, match);
            }

        }

    }

    /**
     * Erzeugt zufällige Team-Paare für eine Runde.
     *
     * @param teams Teams, die für Paarungen berücksichtigt werden
     * @return Liste von Team-Paaren, ggf. mit einem Einzelteam
     */

    @Unmodifiable
    @NotNull
    private List<List<Team>> shuffleTeamList(List<Team> teams) {
        Collections.shuffle(teams);

        return teams.stream().gather(Gatherers.windowFixed(2)).toList();
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

    /**
     * Ermittelt Siegerteams der Vorrunden basierend auf kumulierten Punkten.
     *
     * @param tournament Turnierkontext für die Vorrundenbewertung
     * @return sortierte Siegerliste
     * @implNote Die Punkte werden pro Team aggregiert und absteigend sortiert.
     */
    @NotNull
    private List<Team> getTheWinnersOfCurrentPreRound(@NotNull Tournament tournament) {
        List<Team> winner = new ArrayList<>();
        Map<Team, Double> teams = new HashMap<>();

        for (int i = 0; i < tournament.getPreRound(); i++) {
            List<Match> matches = new SQLiteMatchDao().getAllMatchesFromTournamentInRound(tournament, i);
            for (Match match : matches) {
                match.getPoints().forEach((key, value) -> {
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
                if (counter > sortedMap.size()) break sortingOut;
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
    private List<Team> getTheWinnersOfCurrentRound(Tournament tournament) {

        List<Match> matches =
                new SQLiteMatchDao().getAllMatchesFromTournamentInRound(tournament, tournament.getCurrendRound());
        List<Team> winners = new ArrayList<>();

        for (Match match : matches) {

            Team winner = null;
            for (Map.Entry<Team, Double> entry : match.getPoints().entrySet()) {
                if (winner == null) {
                    winner = entry.getKey();
                } else if (entry.getValue() > match.getPoints().get(winner)) {
                    winner = entry.getKey();
                }
            }

            winners.add(winner);

        }

        return winners;

    }
}
