// File: TeamDaoTest.java
package org.example.calcettomanagmentsystem.dao;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prüft die öffentliche API des {@link TeamDao}.
 */
public class TeamDaoTest {

	/**
	 * Stellt sicher, dass {@link TeamDao} als Interface definiert ist.
	 */
	@Test
	void isInterface() {
		assertTrue(TeamDao.class.isInterface());
	}

	/**
	 * Validiert die erwarteten Methodensignaturen der Schnittstelle.
	 */
	@Test
	void declaresExpectedMethods() {
		List<String> methodNames = Arrays.stream(TeamDao.class.getDeclaredMethods())
		                                 .map(Method::getName)
		                                 .sorted()
		                                 .toList();

		assertEquals(
				List.of(
						"addPlayerToTeam",
						"addTeam",
						"deleteTeam",
						"getAllTeamsFromTournament",
						"getTeamById",
						"getTeamByName"
				),
				methodNames
		);
	}
}
