package org.example.calcettomanagmentsystem.model;

import java.util.Objects;


/**
 * Immutable player value object.
 * Holds id, name, email and associated tournament.
 */
public record Player(int pid, String pname, String pemail, Tournament tournament) {
	@Override
	public String toString() {
		return "Player{" +
				"pid=" + pid +
				", pname='" + pname + '\'' +
				", pemail='" + pemail + '\'' +
				", tournament=" + tournament +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return pid() == player.pid() && Objects.equals(pname(), player.pname()) && Objects.equals(pemail(), player.pemail()) && Objects.equals(tournament(), player.tournament());
	}

	@Override
	public int hashCode() {
		return Objects.hash(pid(), pname(), pemail(), tournament());
	}
}
