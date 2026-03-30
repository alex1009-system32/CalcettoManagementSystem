package org.example.calcettomanagmentsystem.core.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domain object representing a team within a tournament.
 * <p>
 * This record encapsulates team identity and its roster of players.
 * It allows match logic to operate independently of direct database dependencies.
 * </p>
 *
 * @param id Unique identifier for the team.
 * @param name The name of the team.
 * @param players A list of {@link Player} members belonging to this team.
 *
 * @see Player
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public record Team (int id, String name, List<Player> players) {
    /**
     * Constructs a team with a specific ID and name, initializing an empty player list.
     * <p>
     * Typically used for initializing existing teams where player data is loaded later.
     * </p>
     *
     * @param id Unique identifier for the team.
     * @param name Name of the team.
     */
    public Team(int id, String name) {
        this(id, name, new ArrayList<>());
    }

    /**
     * Constructs a team with a name and a default ID of -1, initializing an empty player list.
     * <p>
     * Used for creating a new team that has not yet been saved to the database.
     * </p>
     *
     * @param name Name of the team.
     */
    public Team( String name) {
        this(-1, name, new ArrayList<>());
    }

    /**
     * Compares this team with another object for equality based on ID, name, and roster.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id && Objects.equals(name, team.name) && Objects.equals(players,
                                                                                  team.players);
    }

    /**
     * Computes a hash code for this team based on its properties.
     *
     * @return Hash code value for use in hashed collections.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, players);
    }

    /**
     * Provides a string representation of the team for logging and debugging purposes.
     *
     * @return A formatted string containing team details and player roster.
     */
    @Override
    public @NotNull String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", players=" + players +
                '}';
    }
}
