package org.example.calcettomanagmentsystem.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * Unveränderlicher Spielerwert mit Turnierbezug.
 * <p>
 * Dient als leichtgewichtiges Transportobjekt zwischen UI und Persistenz.
 * </p>
 *
 * @param id eindeutige Spieler-ID
 * @param name Anzeigename des Spielers
 * @param email Kontaktadresse für Identifikation
 * @param tournament Turnierkontext zur Zuordnung
 */
public record Player(int id, String name, String email, Tournament tournament) {
    public Player(String name, String email, Tournament tournament) {
        this(-1, name, email, tournament);
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
		return id() == player.id() && Objects.equals(name(), player.name()) && Objects.equals(email(), player.email()) && Objects.equals(tournament(), player.tournament());
	}

	/**
	 * Erzeugt einen Hash für Collections und Caches.
	 *
	 * @return Hashcode basierend auf Spielerfeldern
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id(), name(), email(), tournament());
	}

    /**
     * Liefert eine lesbare Darstellung für Logs und Debugging.
     *
     * @return textuelle Repräsentation des Spielers
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
