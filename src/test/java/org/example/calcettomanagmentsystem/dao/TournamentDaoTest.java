// File: TournamentDaoTest.java
package org.example.calcettomanagmentsystem.dao;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prüft die öffentliche API des {@link TournamentDao}.
 */
public class TournamentDaoTest {

	/**
	 * Stellt sicher, dass {@link TournamentDao} als Interface definiert ist.
	 */
	@Test
	void isInterface() {
		assertTrue(TournamentDao.class.isInterface());
	}

	/**
	 * Validiert die erwarteten Methodensignaturen der Schnittstelle.
	 */
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
