package org.example.calcettomanagmentsystem.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Match {
    private HashMap<Team, Double> points;
    private List<Team> teams;

    private int mid;
    private int round;

    public Match(int mid, int round) {
        this.points = new HashMap<>();

        setMid(mid);
        setRound(round);
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void addPoints(Team team, double points) {
        this.points.put(team, points);
    }

    public int getRound() {
        return round;
    }

    public int getMid() {
        return mid;
    }

    private void setRound(int round) {
        this.round = round;
    }

    private void setMid(int mid) {
        this.mid = mid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return getMid() == match.getMid() && getRound() == match.getRound() && Objects.equals(points, match.points) && Objects.equals(teams, match.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points, teams, getMid(), getRound());
    }

    @Override
    public String toString() {
        return "Match{" +
                "points=" + points +
                ", teams=" + teams +
                ", mid=" + mid +
                ", round=" + round +
                '}';
    }
}
