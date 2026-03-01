package org.example.calcettomanagmentsystem.model;

import java.util.Objects;


/**
 * Unveränderlicher Spielerwert mit Turnierbezug.
 * <p>
 * Dient als leichtgewichtiges Transportobjekt zwischen UI und Persistenz.
 * </p>
 *
 * @param pid eindeutige Spieler-ID
 * @param pname Anzeigename des Spielers
 * @param pemail Kontaktadresse für Identifikation
 * @param tournament Turnierkontext zur Zuordnung
 */
public record Player(int pid, String pname, String pemail, Tournament tournament) {
	/**
	 * Liefert eine lesbare Darstellung für Logs und Debugging.
	 *
	 * @return textuelle Repräsentation des Spielers
	 */
	@Override
	public String toString() {
		return "Player{" +
				"pid=" + pid +
				", pname='" + pname + '\'' +
				", pemail='" + pemail + '\'' +
				", tournament=" + tournament +
				'}';
	}

	/**
	 * Vergleicht Spieler anhand ihrer zentralen Eigenschaften.
	 *
	 * @param o Vergleichsobjekt
	 * @return {@code true}, wenn die relevanten Felder übereinstimmen
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return pid() == player.pid() && Objects.equals(pname(), player.pname()) && Objects.equals(pemail(), player.pemail()) && Objects.equals(tournament(), player.tournament());
	}

	/**
	 * Erzeugt einen Hash für Collections und Caches.
	 *
	 * @return Hashcode basierend auf Spielerfeldern
	 */
	@Override
	public int hashCode() {
		return Objects.hash(pid(), pname(), pemail(), tournament());
	}
}
