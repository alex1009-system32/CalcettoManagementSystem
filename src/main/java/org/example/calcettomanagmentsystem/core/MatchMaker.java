package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Gatherers;

/**
 * Utilities to generate matches for tournaments, including preliminary rounds.
 */
public class MatchMaker {

    /**
     * Generates preliminary rounds for the tournament if none have started yet.
     *
     * @param tournament tournament context
     */

    public void makePreRounds(Tournament tournament) {
        if (tournament.getCurrendRound() != 0) {
            return;
        }

        List<Team> teams = new SQLiteTeamDao().getAllTeamsFromTournament(tournament);
        SQLiteMatchDao sqliteMatchDao = new SQLiteMatchDao();

        for (int i = 0; i < tournament.getPreRound(); i++) {

            if (i == 0) {

				//ToDo: need to go on further

            }

        }

    }

    /**
     * Generates matches for the current round based on the tournament state.
     *
     * @param tournament tournament context
     */

    public void makeMatchesForRound(Tournament tournament) {

    }

    /**
     * Persists matches for the given team pairings.
     *
     * @param teams      list of team pairs (or single team for bye)
     * @param tournament tournament context
     */

    private void createMatches(List<List<Team>> teams, Tournament tournament) {
        if (teams.size() == 0) return;

        SQLiteMatchDao sqliteMatchDao = new SQLiteMatchDao();
		Match match;

        for (List<Team> teamList : teams) {

			match = sqliteMatchDao.addMatch(tournament);

            for (Team team : teamList) {

                // ToDo : need to update the SQLiteMatchDao.java so that it is useful.

            }

        }

    }

    /**
     *
     * <p>
     * This method returns a List with List that are shuffled(randomized list).
     * </p>
     * <p>
     * The inner list are always a Pair of Teams. <br>
     * If uneven last List element has one Team element.
     * </p>
     *
     * @param teams needs for shuffling
     * @return a List with List of Teams
     */

    @NotNull
    private List<List<Team>> shuffleTeamList(List<Team> teams) {
        Collections.shuffle(teams);

        return teams.stream()
                .gather(Gatherers.windowFixed(2))
                .toList();
    }

    /**
     *
     * <p>
     * This method checks if there are two list objects with the same elements. <br>
     * </p>
     *
     * @param allTeamLists needs at least 2 Lists for comparing.
     * @return returns true if there is a match els false.
     */

    private boolean hasSameTeam(@NotNull List<List<Team>>... allTeamLists) {

        if (allTeamLists.length < 2) return false;

        for (int i = 0; i < allTeamLists.length; i++) {

            for (int j = i + 1; j < allTeamLists.length; j++) {

                if (compareTwo(allTeamLists[i], allTeamLists[j])) {
                    return true;
                }

            }

        }

        return false;

    }

    /**
     *
     * <p>
     * This method is a helper method for {@link #hasSameTeam(List[])}. <br>
     * It looks if these lists have the same two elements or not.
     * </p>
     *
     * @param listA
     * @param listB
     * @return retruns a boolean value for if it has a same team or not.
     */

    private boolean compareTwo(List<List<Team>> listA, List<List<Team>> listB) {

        for (List<Team> teamList1 : listA) {

            for (List<Team> teamList2 : listB) {

                if (
                        teamList1.contains(teamList2.getFirst()) &&
                                teamList1.contains(teamList2.getLast())) {
                    return true;
                }

            }

        }

        return false;

    }
}

