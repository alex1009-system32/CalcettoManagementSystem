package org.example.calcettomanagmentsystem.model;

import java.util.Arrays;

public class Team {

    private int tid;
    private String teamName;

    private Player[] players;

    public Team(int tid, String teamName, Player firstPlayer, Player lastPlayer) {
        this.players = new Player[2];

        setTid(tid);
        setTeamName(teamName);

        players[0] = firstPlayer;
        players[1] = lastPlayer;
    }

    public int getTid() {
        return tid;
    }

    public String getTeamName() {
        return teamName;
    }

    public Player getFirstPlayer() {
        return players[0];
    }

    public Player getLastPlayer() {
        return players[1];
        };

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
                ", players=" + Arrays.toString(players) +
                '}';
    }
}
