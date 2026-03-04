package org.example.calcettomanagmentsystem.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
Team (int id, String name, List<Player> players) {
    public Team(int id, String name) {
        this(id, name, new ArrayList<>());
    }
    public Team( String name) {
        this(-1, name, new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id && Objects.equals(name, team.name) && Objects.equals(players,
                                                                                  team.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, players);
    }

    @Override
    public @NotNull String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", players=" + players +
                '}';
    }
}
