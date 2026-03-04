package org.example.calcettomanagmentsystem.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Domänenobjekt für ein Team innerhalb eines Turniers.
 * <p>
 * Die Klasse hält Teamidentität und Kader zusammen, damit Match-Logik
 * ohne direkte Datenbankabhängigkeit arbeiten kann.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.model.Player
 */
public record
Team (int tid, String teamName, List<Player> players) {

    public Team(int tid, String teamName) {
        this(tid, teamName, new ArrayList<>());
    }

    public Team( String teamName) {
        this(-1, teamName, new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return tid == team.tid && Objects.equals(teamName, team.teamName) && Objects.equals(players,
                                                                                            team.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid, teamName, players);
    }

    @Override
    public String toString() {
        return "Team{" +
                "tid=" + tid +
                ", teamName='" + teamName + '\'' +
                ", players=" + players +
                '}';
    }
}
