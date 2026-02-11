package org.example.calcettomanagmentsystem.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public record Player(int pid, String pname, String pemail) {
	@NotNull
	@Override
	public String toString() {
		return "Player{" + "pid=" + pid + ", pname='" + pname + '\'' + ", pemail='" + pemail + '\'' + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return pid() == player.pid() && Objects.equals(pname(), player.pname()) && Objects.equals(pemail(), player.pemail());
	}

	@Override
	public int hashCode() {
		return Objects.hash(pid(), pname(), pemail());
	}
}
