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

	public Tournament(int tid, String tournamentName, String date, long duration) {
		setTid(tid);
		setTournamentName(tournamentName);
		setDate(LocalDate.parse(date));
		setDuration(duration);
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

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Tournament that = (Tournament) o;
		return getTid() == that.getTid() && getDuration() == that.getDuration() && Objects.equals(getTournamentName(), that.getTournamentName()) && Objects.equals(getDate(), that.getDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTid(), getTournamentName(), getDate(), getDuration());
	}

	@Override
	public String toString() {
		return "Tournament{" + "tid=" + tid + ", tournamentName='" + tournamentName + '\'' + ", date=" + date + ", duration=" + duration + '}';
	}
}
