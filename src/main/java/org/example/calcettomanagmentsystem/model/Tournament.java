package org.example.calcettomanagmentsystem.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Tournament {

	private int tid;
	private String tournamentName;
	private LocalDate date;
	private long duration;
	private int preRound;
	private int currendRound;
	private int maxTeamSize;

	public Tournament(int tid, String tournamentName, LocalDate date, long duration, int preRound, int currendRound, int maxTeamSize) {
		this.tid = tid;
		this.tournamentName = tournamentName;
		this.date = date;
		this.duration = duration;
		this.preRound = preRound;
		this.currendRound = currendRound;
		this.maxTeamSize = maxTeamSize;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getPreRound() {
		return preRound;
	}

	public void setPreRound(int preRound) {
		this.preRound = preRound;
	}

	public int getCurrendRound() {
		return currendRound;
	}

	public void setCurrendRound(int currendRound) {
		this.currendRound = currendRound;
	}

	public int getMaxTeamSize() {
		return maxTeamSize;
	}

	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Tournament that = (Tournament) o;
		return getTid() == that.getTid() && getDuration() == that.getDuration() && getPreRound() == that.getPreRound() && getCurrendRound() == that.getCurrendRound() && getMaxTeamSize() == that.getMaxTeamSize() && Objects.equals(getTournamentName(), that.getTournamentName()) && Objects.equals(getDate(), that.getDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTid(), getTournamentName(), getDate(), getDuration(), getPreRound(), getCurrendRound(), getMaxTeamSize());
	}

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
