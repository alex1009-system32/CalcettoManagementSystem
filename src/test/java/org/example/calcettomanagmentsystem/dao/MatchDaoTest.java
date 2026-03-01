
package org.example.calcettomanagmentsystem.dao;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prüft die öffentliche API des {@link MatchDao}.
 */
public class MatchDaoTest {

	/**
	 * Stellt sicher, dass {@link MatchDao} als Interface definiert ist.
	 */
	@Test
	void isInterface() {
		assertTrue(MatchDao.class.isInterface());
	}

	/**
	 * Validiert die erwartete Methodensignatur der Schnittstelle.
	 */
	@Test
	void declaresExpectedMethods() {
		List<String> methodNames = Arrays.stream(MatchDao.class.getDeclaredMethods())
		                                 .map(Method::getName)
		                                 .sorted()
		                                 .toList();

		assertEquals(
				List.of(
						"addAddPointToTeamInMatch",
						"addMatch",
						"addTeamToMatch",
						"deleteMatch",
						"getAllMatchesFromTeam",
						"getAllMatchesFromTournament",
						"getAllMatchesFromTournamentInRound",
						"getMatchById"
				),
				methodNames
		);
	}
}
