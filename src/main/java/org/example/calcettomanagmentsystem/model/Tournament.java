package org.example.calcettomanagmentsystem.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Domain model for a tournament.
 * Stores metadata like name, start date, duration, current and preliminary rounds,
 * and the maximum team size for team generation.
 */
public class Tournament {

	private int tid;
	private String tournamentName;
	private LocalDate date;
	private long duration;
	private int preRound;
	private int currendRound;
	private int maxTeamSize;

	/**
	 * Creates a tournament with the given attributes.
	 *
	 * @param tid unique id
	 * @param tournamentName display name
	 * @param date start date
	 * @param duration duration in days
	 * @param preRound number of preliminary rounds before the main bracket
	 * @param currendRound current round pointer
	 * @param maxTeamSize maximum number of players per team
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
	 * @return tournament id
	 */
	public int getTid() {
		return tid;
	}

	/**
	 * @param tid tournament id
	 */
	public void setTid(int tid) {
		this.tid = tid;
	}

	/**
	 * @return tournament name
	 */
	public String getTournamentName() {
		return tournamentName;
	}

	/**
	 * @param tournamentName tournament name
	 */
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	/**
	 * @return start date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date start date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return duration in days
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration duration in days
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * @return number of preliminary rounds
	 */
	public int getPreRound() {
		return preRound;
	}

	/**
	 * @param preRound number of preliminary rounds
	 */
	public void setPreRound(int preRound) {
		this.preRound = preRound;
	}

	/**
	 * @return current round
	 */
	public int getCurrendRound() {
		return currendRound;
	}

	/**
	 * @param currendRound current round
	 */
	public void setCurrendRound(int currendRound) {
		this.currendRound = currendRound;
	}

	/**
	 * @return max players per team
	 */
	public int getMaxTeamSize() {
		return maxTeamSize;
	}

	/**
	 * @param maxTeamSize max players per team
	 */
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
