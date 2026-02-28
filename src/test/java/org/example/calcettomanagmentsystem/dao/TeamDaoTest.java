// File: TeamDaoTest.java
package org.example.calcettomanagmentsystem.dao;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamDaoTest {

	@Test
	void isInterface() {
		assertTrue(TeamDao.class.isInterface());
	}

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