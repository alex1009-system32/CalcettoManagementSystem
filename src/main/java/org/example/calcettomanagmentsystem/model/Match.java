package org.example.calcettomanagmentsystem.model;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Domänenobjekt für ein Match innerhalb eines Turniers.
 * <p>
 * Hält Teams und Punktstände zusammen, um Ergebnisse nachvollziehbar zu machen.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.model.Team
 */
public record Match (int mid, int round, @NotNull Tournament tournament, Map<Team, Double> teamResults){
    public Match(int mid, int round, Tournament tournament) {
        this (mid, round, tournament, new LinkedHashMap<>());
    }

    public Match(int round, Tournament tournament) {
        this(-1, round, tournament, new LinkedHashMap<>());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return mid == match.mid && round == match.round && Objects.equals(tournament,
                                                                          match.tournament) && Objects.equals(
                teamResults,
                match.teamResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, round, tournament, teamResults);
    }

    @Override
    public String toString() {
        return "Match{" +
                "mid=" + mid +
                ", round=" + round +
                ", tournament=" + tournament +
                ", teamResults=" + teamResults +
                '}';
    }
}
