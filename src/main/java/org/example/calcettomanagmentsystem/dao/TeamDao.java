package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface TeamDao extends GeneralDao<Team> {
    Team addPlayer(Team team, Player player);
    List<Team> getTeams(Tournament tournament);
}
