package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.core.model.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Gatherers;

/**
 * Component for randomizing team ordering and grouping.
 * <p>
 * This utility class provides methods to shuffle team lists and 
 * partition them into fixed-size groups for match generation.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class TeamShuffler {

    /**
     * Shuffles a list of teams and partitions them into groups.
     *
     * @param teams The list of teams to shuffle.
     * @param teamSize The target group size.
     * @return A shuffled and partitioned list of team groups.
     */
    @Unmodifiable
    @NotNull
    public List<List<Team>> shuffleTeamList(List<Team> teams, int teamSize) {
        List<Team> mutableTeams = new ArrayList<>(teams);
        Collections.shuffle(mutableTeams);
        return mutableTeams.stream().gather(Gatherers.windowFixed(teamSize)).toList();
    }
}
