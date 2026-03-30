package org.example.calcettomanagmentsystem.core.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Domain model representing a tournament with all parameters necessary for control.
 * <p>
 * This class serves as a ubiquitous language between UI, persistence, and logic layers,
 * ensuring consistent tournament state across the application.
 * </p>
 *
 * @param id Unique identifier for the tournament.
 * @param name The name of the tournament.
 * @param date The starting date of the tournament.
 * @param duration Expected duration of the tournament in days.
 * @param preRound Number of preliminary rounds.
 * @param currentRound Current round number in progress.
 * @param maxTeamSize Maximum number of players allowed per team.
 *
 * @see org.example.calcettomanagmentsystem.core.MatchMaker
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public record Tournament(int id, String name, LocalDate date, long duration, int preRound, int currentRound, int maxTeamSize) {
    /**
     * Constructs a tournament with a specific ID, name, duration, and other parameters, using today's date.
     * <p>
     * This constructor allows specifying the current round state, useful for resuming existing tournaments.
     * </p>
     *
     * @param id Unique identifier.
     * @param name Tournament name.
     * @param duration Duration in days.
     * @param preRound Number of preliminary rounds.
     * @param currentRound The current round.
     * @param maxTeamSize Max players per team.
     */
    public Tournament(int id, String name, long duration, int preRound, int currentRound, int maxTeamSize) {
        this(id, name, LocalDate.now(), duration, preRound, currentRound, maxTeamSize);
    }

    /**
     * Constructs a new tournament with a name, duration, and constraints, assigning a default round of 0 and current date.
     * <p>
     * Usually invoked when creating a new tournament that starts immediately from the first phase.
     * </p>
     *
     * @param id Unique identifier.
     * @param name Tournament name.
     * @param duration Duration in days.
     * @param preRound Preliminary rounds count.
     * @param maxTeamSize Maximum roster size.
     */
    public Tournament(int id, String name, long duration, int preRound, int maxTeamSize) {
        this(id, name, LocalDate.now(), duration, preRound, 0, maxTeamSize);
    }

    /**
     * Constructs a new tournament without an initial ID (defaults to -1).
     * <p>
     * The preferred constructor for new tournament creation from user input.
     * </p>
     *
     * @param name Tournament name.
     * @param duration Duration in days.
     * @param preRound Preliminary rounds count.
     * @param maxTeamSize Maximum roster size.
     */
    public Tournament(String name, long duration, int preRound, int maxTeamSize) {
        this(-1, name, LocalDate.now(), duration, preRound, 0, maxTeamSize);
    }

    /**
     * Compares this tournament with another object for equality based on all fields.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return id == that.id && preRound == that.preRound && duration == that.duration && maxTeamSize == that.maxTeamSize && currentRound == that.currentRound && Objects.equals(
                date,
                that.date) && Objects.equals(name, that.name);
    }

    /**
     * Computes a hash code for this tournament based on its properties.
     *
     * @return Hash code value for use in hashed collections.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, duration, preRound, currentRound, maxTeamSize);
    }

    /**
     * Provides a string representation of the tournament for logging and debugging purposes.
     *
     * @return A formatted string containing tournament details and configuration.
     */
    @Override
    public @NotNull String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", preRound=" + preRound +
                ", currentRound=" + currentRound +
                ", maxTeamSize=" + maxTeamSize +
                '}';
    }
}
