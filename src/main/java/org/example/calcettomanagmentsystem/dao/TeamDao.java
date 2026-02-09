package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface TeamDao {
    void addTeamFromTournament(String teamname, Tournament tournament);
    void addPlayerToTeam(int tid, Player player);
    void addPlayerToTeam(String teamname, Player player);
    List<Team>getAllTeamsFromTournament (Tournament tournament);
    Team getTeamById(int tid);
}
