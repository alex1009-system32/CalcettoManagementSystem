
package org.example.calcettomanagmentsystem.dao;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchDaoTest {

	@Test
	void isInterface() {
		assertTrue(MatchDao.class.isInterface());
	}

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