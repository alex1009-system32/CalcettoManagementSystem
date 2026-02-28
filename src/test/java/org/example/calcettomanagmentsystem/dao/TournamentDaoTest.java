// File: TournamentDaoTest.java
package org.example.calcettomanagmentsystem.dao;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TournamentDaoTest {

	@Test
	void isInterface() {
		assertTrue(TournamentDao.class.isInterface());
	}

	@Test
	void declaresExpectedMethods() {
		List<String> methodNames = Arrays.stream(TournamentDao.class.getDeclaredMethods())
		                                 .map(Method::getName)
		                                 .sorted()
		                                 .toList();

		assertEquals(
				List.of(
						"addTournament",
						"deleteTournament",
						"getAllTournaments",
						"getTournamentById",
						"increaseRound"
				),
				methodNames
		);
	}
}