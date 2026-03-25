package org.example.calcettomanagmentsystem.core.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * Represents an immutable player value with tournament association.
 * <p>
 * This record serves as a lightweight data transfer object between the UI and persistence layers.
 * It encapsulates player identity and contact information within a specific tournament context.
 * </p>
 *
 * @param id Unique identifier for the player.
 * @param name Display name of the player.
 * @param email Contact email address for identification.
 * @param tournament The tournament context this player is associated with.
 *
 * @author Senior Developer
 */
public record Player(int id, String name, String email, Tournament tournament) {
    /**
     * Constructs a new Player with a default ID of -1.
     *
     * @param name Display name of the player.
     * @param email Contact email address.
     * @param tournament Associated tournament context.
     */
    public Player(String name, String email, Tournament tournament) {
        this(-1, name, email, tournament);
    }

    /**
     * Compares this player with another object for equality based on all fields.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id() == player.id() && Objects.equals(name(), player.name()) && Objects.equals(email(), player.email()) && Objects.equals(tournament(), player.tournament());
    }

    /**
     * Computes a hash code for this player based on its properties.
     *
     * @return Hash code value for use in hashed collections.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id(), name(), email(), tournament());
    }

    /**
     * Provides a string representation of the player for logging and debugging purposes.
     *
     * @return A formatted string containing player details.
     */
    @Override
    public @NotNull String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tournament=" + tournament +
                '}';
    }
}
