package org.example.calcettomanagmentsystem.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team {

    private int tid;
    private String teamName;

    private List<Player> players;

    public Team(int tid, String teamName) {
        this.players = new ArrayList<Player>();

        setTid(tid);
        setTeamName(teamName);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public int getTid() {
        return tid;
    }

    public String getTeamName() {
        return teamName;
    }

    private void setTid(int tid) {
        this.tid = tid;
    }

    private void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "Team{" +
                "tid=" + tid +
                ", teamName='" + teamName + '\'' +
                ", players=" + Arrays.toString(players.toArray()) +
                '}';
    }
}
