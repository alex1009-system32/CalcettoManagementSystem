package org.example.calcettomanagmentsystem.model;

import java.util.Arrays;
import java.util.HashMap;

public class Match {

    private HashMap<Team, Integer> points;
    private Team[] teams;

    private int round;

    public Match(int round, Team FirstTeam, Team LastTeam) {
        this.points = new HashMap<>();

        this.points.put(FirstTeam, 0);
        this.points.put(LastTeam, 0);

        this.teams = new Team[]{FirstTeam, LastTeam};

        setRound(round);
    }

    public int getRound() {
        return round;
    }

    public HashMap<Team, Integer> getTeams() {
        return points;
    }

    public Integer getPointsFromFirstTeam() {
        return points.get(teams[0]);
    }

    public Integer getPointsFromLastTeam() {
        return points.get(teams[1]);
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setPointsForFirstTeam(int points) {
        this.points.put(teams[0], points);
    }

    public void setPointsForLastTeam(int points) {
        this.points.put(teams[1], points);
    }

    @Override
    public String toString() {
        return "Match{" +
                "points=" + points +
                ", teams=" + Arrays.toString(teams) +
                ", round=" + round +
                '}';
    }
}
