package org.example.calcettomanagmentsystem.model;

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
public record Tournament(int tid, String tournamentName, LocalDate date, long duration, int preRound, int currentRound, int maxTeamSize) {
    public Tournament(int tid, String tournamentName, long duration, int preRound, int maxTeamSize) {
        this(tid, tournamentName, LocalDate.now(), duration, preRound, 0, maxTeamSize);
    }

    public Tournament(String tournamentName, long duration, int preRound, int maxTeamSize) {
        this(-1, tournamentName, LocalDate.now(), duration, preRound, 0, maxTeamSize);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return tid == that.tid && preRound == that.preRound && duration == that.duration && maxTeamSize == that.maxTeamSize && currentRound == that.currentRound && Objects.equals(
                date,
                that.date) && Objects.equals(tournamentName, that.tournamentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid, tournamentName, date, duration, preRound, currentRound, maxTeamSize);
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "tid=" + tid +
                ", tournamentName='" + tournamentName + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", preRound=" + preRound +
                ", currentRound=" + currentRound +
                ", maxTeamSize=" + maxTeamSize +
                '}';
    }
}
