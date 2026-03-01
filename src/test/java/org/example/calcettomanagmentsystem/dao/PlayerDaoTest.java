// File: PlayerDaoTest.java
package org.example.calcettomanagmentsystem.dao;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prüft die öffentliche API des {@link PlayerDao}.
 */
public class PlayerDaoTest {

	/**
	 * Stellt sicher, dass {@link PlayerDao} als Interface definiert ist.
	 */
	@Test
	void isInterface() {
		assertTrue(PlayerDao.class.isInterface());
	}

	/**
	 * Validiert die erwarteten Methodensignaturen der Schnittstelle.
	 */
	@Test
	void declaresExpectedMethods() {
		List<String> methodNames = Arrays.stream(PlayerDao.class.getDeclaredMethods())
		                                 .map(Method::getName)
		                                 .sorted()
		                                 .toList();

		assertEquals(
				List.of(
						"addPlayer",
						"deletePlayer",
						"getAllPlayers",
						"getAllPlayersFromTournament",
						"getPlayerById"
				),
				methodNames
		);
	}
}
