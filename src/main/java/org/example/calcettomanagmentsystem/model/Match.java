package org.example.calcettomanagmentsystem.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Match {
    private HashMap<Team, Integer> points;
    private List<Team> teams;

    private int mid;
    private int round;

    public Match(int mid, int round) {
        this.points = new HashMap<>();

        setMid(mid);
        setRound(round);
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
    public String toString() {
        return "Match{" +
                "points=" + points +
                ", teams=" + teams +
                ", mid=" + mid +
                ", round=" + round +
                '}';
    }
}
