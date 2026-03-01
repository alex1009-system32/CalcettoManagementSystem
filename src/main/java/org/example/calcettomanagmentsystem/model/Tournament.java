package org.example.calcettomanagmentsystem.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
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
public class Tournament {

	/**
	 * Primärschlüssel zur eindeutigen Identifikation.
	 */
	private int tid;
	/**
	 * Anzeigename, der in der UI verwendet wird.
	 */
	private String tournamentName;
	/**
	 * Startdatum zur zeitlichen Einordnung.
	 */
	private LocalDate date;
	/**
	 * Geplante Dauer in Tagen für Scheduling.
	 */
	private long duration;
	/**
	 * Anzahl der Vorrunden für die Match-Planung.
	 */
	private int preRound;
	/**
	 * Aktuelle Runde, die den Fortschritt markiert.
	 */
	private int currendRound;
	/**
	 * Maximale Teamgröße zur Team-Erzeugung.
	 */
	private int maxTeamSize;

	/**
	 * Erstellt ein Turnier mit allen Planungsparametern.
	 *
	 * @param tid eindeutige ID zur Persistenz
	 * @param tournamentName Anzeigename für UI und Berichte
	 * @param date Startdatum für die zeitliche Einordnung
	 * @param duration geplante Dauer in Tagen
	 * @param preRound Anzahl der Vorrunden
	 * @param currendRound aktuelle Runde für Fortschritt
	 * @param maxTeamSize maximale Teamgröße
	 */
	public Tournament(int tid, String tournamentName, LocalDate date, long duration, int preRound, int currendRound, int maxTeamSize) {
		this.tid = tid;
		this.tournamentName = tournamentName;
		this.date = date;
		this.duration = duration;
		this.preRound = preRound;
		this.currendRound = currendRound;
		this.maxTeamSize = maxTeamSize;
	}

	/**
	 * Liefert die Turnier-ID für Referenzen.
	 *
	 * @return Turnier-ID
	 */
	public int getTid() {
		return tid;
	}

	/**
	 * Setzt die Turnier-ID, z. B. nach Persistenz.
	 *
	 * @param tid eindeutige Turnier-ID
	 */
	public void setTid(int tid) {
		this.tid = tid;
	}

	/**
	 * Liefert den Anzeigenamen des Turniers.
	 *
	 * @return Turniername
	 */
	public String getTournamentName() {
		return tournamentName;
	}

	/**
	 * Setzt den Anzeigenamen für UI und Berichte.
	 *
	 * @param tournamentName neuer Turniername
	 */
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	/**
	 * Liefert das Startdatum des Turniers.
	 *
	 * @return Startdatum
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Setzt das Startdatum für Planung und Anzeige.
	 *
	 * @param date neues Startdatum
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Liefert die geplante Dauer in Tagen.
	 *
	 * @return Dauer in Tagen
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Setzt die geplante Dauer in Tagen.
	 *
	 * @param duration neue Dauer in Tagen
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * Liefert die Anzahl der Vorrunden.
	 *
	 * @return Vorrundenanzahl
	 */
	public int getPreRound() {
		return preRound;
	}

	/**
	 * Setzt die Anzahl der Vorrunden.
	 *
	 * @param preRound neue Vorrundenanzahl
	 */
	public void setPreRound(int preRound) {
		this.preRound = preRound;
	}

	/**
	 * Liefert die aktuelle Runde für Fortschrittslogik.
	 *
	 * @return aktuelle Runde
	 */
	public int getCurrendRound() {
		return currendRound;
	}

	/**
	 * Setzt die aktuelle Runde, um Fortschritt zu persistieren.
	 *
	 * @param currendRound neue aktuelle Runde
	 */
	public void setCurrendRound(int currendRound) {
		this.currendRound = currendRound;
	}

	/**
	 * Liefert die maximale Teamgröße.
	 *
	 * @return maximale Teamgröße
	 */
	public int getMaxTeamSize() {
		return maxTeamSize;
	}

	/**
	 * Setzt die maximale Teamgröße für Teamgenerierung.
	 *
	 * @param maxTeamSize neue maximale Teamgröße
	 */
	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}

	/**
	 * Vergleicht Turniere anhand ihrer strukturellen Eigenschaften.
	 *
	 * @param o Vergleichsobjekt
	 * @return {@code true}, wenn alle relevanten Felder übereinstimmen
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Tournament that = (Tournament) o;
		return getTid() == that.getTid() && getDuration() == that.getDuration() && getPreRound() == that.getPreRound() && getCurrendRound() == that.getCurrendRound() && getMaxTeamSize() == that.getMaxTeamSize() && Objects.equals(getTournamentName(), that.getTournamentName()) && Objects.equals(getDate(), that.getDate());
	}

	/**
	 * Liefert einen Hash zur konsistenten Nutzung in Collections.
	 *
	 * @return Hashcode basierend auf Turnierfeldern
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getTid(), getTournamentName(), getDate(), getDuration(), getPreRound(), getCurrendRound(), getMaxTeamSize());
	}

	/**
	 * Liefert eine lesbare Darstellung für Logs und Debugging.
	 *
	 * @return textuelle Repräsentation des Turniers
	 */
	@Override
	public String toString() {
		return "Tournament{" +
				"tid=" + tid +
				", tournamentName='" + tournamentName + '\'' +
				", date=" + date +
				", duration=" + duration +
				", preRound=" + preRound +
				", currendRound=" + currendRound +
				", maxTeamSize=" + maxTeamSize +
				'}';
	}
}
