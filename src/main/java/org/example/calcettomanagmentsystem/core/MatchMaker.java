package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

/**
 * Utilities to generate matches for tournaments, including preliminary rounds.
 * <p>
 * WARNING: This class contains critical logic issues that need to be addressed:
 * 1. Potential infinite loop in preliminary round generation.
 * 2. Inefficient database usage (N+1 queries).
 * 3. Round increment logic might be incorrect.
 * </p>
 */
public class MatchMaker {

	/**
	 * Generates preliminary rounds for the tournament if none have started yet.
	 * <p>
	 * <b>CRITICAL WARNING:</b> This method attempts to generate unique team pairings for multiple rounds
	 * by shuffling and retrying. If the number of possible unique combinations is exhausted or difficult to find
	 * randomly, this method will enter an INFINITE LOOP.
	 * </p>
	 *
	 * @param tournament tournament context
	 */
	public void makePreRounds(Tournament tournament) {
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
	 * Generates matches for the current round based on the tournament state.
	 *
	 * @param tournament tournament context
	 */
	public void makeMatchesForRound(Tournament tournament) {

		List<Team> teams;
		SQLiteMatchDao sqliteMatchDao = new SQLiteMatchDao();
		Match match;

		if (tournament.getCurrendRound() == tournament.getPreRound()) {
			teams = getTheWinnersOfCurrentPreRound(tournament);
		} else {
			teams = getTheWinnersOfCurrentRound(tournament);
		}

		List<List<Team>> newTeams = IntStream.range(0, teams.size() / 2)
		                                     .mapToObj(i -> Arrays.asList(teams.get(i), teams.get(teams.size() - 1 - i)))
		                                     .collect(Collectors.toList());

		createMatches(newTeams, tournament);
		new SQLiteTournamentDao().increaseRound(tournament);

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
				sqliteMatchDao.addTeamToMatch(team, match);
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

		return teams.stream().gather(Gatherers.windowFixed(2)).toList();
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
	 *
	 * <p>
	 * This method is a helper method for {@link #hasSameTeam(List)}. <br>
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

				if (teamList1.contains(teamList2.getFirst()) && teamList1.contains(teamList2.getLast())) {
					return true;
				}

			}

		}

		return false;

	}

	private List<Team> getTheWinnersOfCurrentPreRound(Tournament tournament) {
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
		                                   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
				;

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

	private List<Team> getTheWinnersOfCurrentRound(Tournament tournament) {

		List<Match> matches = new SQLiteMatchDao().getAllMatchesFromTournamentInRound(tournament, tournament.getCurrendRound());
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