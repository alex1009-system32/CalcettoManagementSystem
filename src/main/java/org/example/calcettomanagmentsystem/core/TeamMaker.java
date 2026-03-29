package org.example.calcettomanagmentsystem.core;

import com.github.javafaker.Faker;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;


/**
 * Component responsible for creating teams from a pool of players.
 * <p>
 * This class uses a partitioning strategy to group players into teams
 * and assigns them random names using the Faker library.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class TeamMaker {

    /**
     * Creates a list of {@link Team} objects by partitioning the provided players.
     *
     * @param players The pool of players to group.
     * @param teamSize The target number of players per team.
     * @return A list of newly created teams with rosters and random names.
     */
    public List<Team> makeTeams(List<Player> players, int teamSize) {
        String name;
        Team team;
        Faker faker = new Faker();

        List<Team> returnList = new ArrayList<>();
        List<List<Player>> teams = partitionTeams(players, teamSize);

        for (List<Player> teamList : teams) {
            name = faker.funnyName().name();
            team = new Team(name);
            for (Player player : teamList) {
                team.players().add(player);
            }

            returnList.add(team);
        }

        return returnList;
    }

    /**
     * Partitions a list of players into fixed-size groups.
     *
     * @param players The list of players to partition.
     * @param teamSize The size of each partition.
     * @return A list of player groups.
     */
    @NotNull
    private @Unmodifiable List<List<Player>> partitionTeams(@NotNull List<Player> players, int teamSize) {
        return players.stream().gather(Gatherers.windowFixed(teamSize)).toList();
    }
}
