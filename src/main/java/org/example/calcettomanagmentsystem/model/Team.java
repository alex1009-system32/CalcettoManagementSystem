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
public class Team {

	/**
	 * Primärschlüssel zur eindeutigen Identifikation.
	 */
	private int tid;
	/**
	 * Anzeigename des Teams für UI und Auswertungen.
	 */
	private String teamName;

	/**
	 * Kaderliste der zugeordneten Spieler.
	 */
	private final List<Player> players;

	/**
	 * Erstellt ein Team mit Identität und Anzeigename.
	 *
	 * @param tid eindeutige Team-ID
	 * @param teamName Teamname für UI und Berichte
	 */
	public Team(int tid, String teamName) {
		this.players = new ArrayList<Player>();

		setTid(tid);
		setTeamName(teamName);
	}

	/**
	 * Fügt einen Spieler dem Kader hinzu, um die Teamzugehörigkeit zu spiegeln.
	 *
	 * @param player Spieler, der dem Team beitritt
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}

	/**
	 * Prüft, ob der Spieler bereits Teil des Kaders ist.
	 *
	 * @param player zu prüfender Spieler
	 * @return {@code true}, wenn der Spieler im Kader enthalten ist
	 */
	public boolean containsPlayer(Player player) {
		return players.contains(player);
	}

	/**
	 * Entfernt einen Spieler, z. B. bei Teamwechsel.
	 *
	 * @param player Spieler, der entfernt werden soll
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}

	/**
	 * Liefert die Team-ID für Referenzen.
	 *
	 * @return Team-ID
	 */
	public int getTid() {
		return tid;
	}

	/**
	 * Liefert den Teamnamen für die Anzeige.
	 *
	 * @return Teamname
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * Setzt die Team-ID intern, um Konsistenz zu wahren.
	 *
	 * @param tid eindeutige Team-ID
	 */
	private void setTid(int tid) {
		this.tid = tid;
	}

	/**
	 * Setzt den Teamnamen intern, um Konsistenz zu wahren.
	 *
	 * @param teamName neuer Teamname
	 */
	private void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * Vergleicht Teams anhand Identität und Kader.
	 *
	 * @param o Vergleichsobjekt
	 * @return {@code true}, wenn die relevanten Felder übereinstimmen
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Team team = (Team) o;
		return getTid() == team.getTid() && Objects.equals(getTeamName(), team.getTeamName()) && Objects.equals(players, team.players);
	}

	/**
	 * Erzeugt einen Hash für Collections und Caches.
	 *
	 * @return Hashcode basierend auf Teamdaten
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getTid(), getTeamName(), players);
	}

	/**
	 * Liefert eine lesbare Darstellung für Logs und Debugging.
	 *
	 * @return textuelle Repräsentation des Teams
	 */
	@Override
	public String toString() {
		return "Team{" + "tid=" + tid + ", teamName='" + teamName + '\'' + ", players=" + Arrays.toString(players.toArray()) + '}';
	}
}
