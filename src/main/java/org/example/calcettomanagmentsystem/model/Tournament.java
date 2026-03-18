package org.example.calcettomanagmentsystem.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Domänenmodell für ein Turnier mit allen für die Steuerung relevanten Parametern.
 * <p>
 * Die Klasse dient als gemeinsame Sprache zwischen UI, Persistenz und Logik,
 * um Turnierzustand konsistent zu halten.
 * </p>
 *
 * @see org.example.calcettomanagmentsystem.core.MatchMaker
 */
public record Tournament(int id, String name, LocalDate date, long duration, int preRound, int currentRound, int maxTeamSize) {
    public Tournament(int id, String name, long duration, int preRound, int currentRound, int maxTeamSize) {
        this(id, name, LocalDate.now(), duration, preRound, currentRound, maxTeamSize);
    }
    public Tournament(int id, String name, long duration, int preRound, int maxTeamSize) {
        this(id, name, LocalDate.now(), duration, preRound, 0, maxTeamSize);
    }
    public Tournament(String name, long duration, int preRound, int maxTeamSize) {
        this(-1, name, LocalDate.now(), duration, preRound, 0, maxTeamSize);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return id == that.id && preRound == that.preRound && duration == that.duration && maxTeamSize == that.maxTeamSize && currentRound == that.currentRound && Objects.equals(
                date,
                that.date) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, duration, preRound, currentRound, maxTeamSize);
    }

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
