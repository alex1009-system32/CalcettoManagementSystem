package org.example.calcettomanagmentsystem.core.model;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Domain object representing a single match within a tournament.
 * <p>
 * This record aggregates the competing teams and their respective score results
 * to provide a comprehensive view of match outcomes.
 * </p>
 *
 * @param id Unique identifier for the match.
 * @param round The tournament round number in which this match occurs.
 * @param tournament The {@link Tournament} this match is part of.
 * @param teamResults A map containing the participating {@link Team}s and their scores.
 *
 * @see Team
 * @author Alex Kerschbamer
 * @version 0.1
 */
public record Match (int id, int round, @NotNull Tournament tournament, Map<Team, Double> teamResults){
    /**
     * Constructs a match with a specific ID, round, and tournament, initializing an empty results map.
     *
     * @param mid Unique identifier for the match.
     * @param round Round number of the match.
     * @param tournament Associated tournament context.
     */
    public Match(int mid, int round, Tournament tournament) {
        this (mid, round, tournament, new LinkedHashMap<>());
    }

    /**
     * Constructs a match with a round and tournament, assigning a default ID of -1 and initializing an empty results map.
     *
     * @param round Round number of the match.
     * @param tournament Associated tournament context.
     */
    public Match(int round, Tournament tournament) {
        this(-1, round, tournament, new LinkedHashMap<>());
    }

    /**
     * Generates a descriptive string for the match based on the names of participating teams.
     *
     * @return A string formatted as "Team1 vs. Team2".
     */
    public String createMatchName() {
        List<String> names = new ArrayList<>();
        for (Map.Entry<Team, Double> entry : teamResults().entrySet()) {
            names.add(entry.getKey().name());
        }
        return String.join(" vs. ", names);
    }

    /**
     * Compares this match with another object for equality based on all fields.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return id == match.id && round == match.round && Objects.equals(tournament,
                                                                        match.tournament) && Objects.equals(
                teamResults,
                match.teamResults);
    }

    /**
     * Computes a hash code for this match based on its properties.
     *
     * @return Hash code value for use in hashed collections.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, round, tournament, teamResults);
    }

    /**
     * Provides a string representation of the match for logging and debugging purposes.
     *
     * @return A formatted string containing match details, round, and team results.
     */
    @Override
    public @NotNull String toString() {
        return "Match{" +
                "id=" + id +
                ", round=" + round +
                ", tournament=" + tournament +
                ", teamResults=" + teamResults +
                '}';
    }
}
