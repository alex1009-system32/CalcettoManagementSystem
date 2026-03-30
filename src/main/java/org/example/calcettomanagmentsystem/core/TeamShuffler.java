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
 * @version 0.1
 * @since 1.0
 */
public class TeamShuffler {

    /**
     * Shuffles a list of teams and partitions them into groups for match generation.
     * <p>
     * This method ensures that match pairings are randomized by shuffling the input 
     * list before applying the partitioning logic.
     * </p>
     *
     * @param teams The list of teams to be randomized.
     * @param teamSize The target group size for the resulting partitions.
     * @return A {@link List} of team groups, where each group is represented as a list.
     */
    @Unmodifiable
    @NotNull
    public List<List<Team>> shuffleTeamList(List<Team> teams, int teamSize) {
        List<Team> mutableTeams = new ArrayList<>(teams);
        Collections.shuffle(mutableTeams);
        return mutableTeams.stream().gather(Gatherers.windowFixed(teamSize)).toList();
    }
}
