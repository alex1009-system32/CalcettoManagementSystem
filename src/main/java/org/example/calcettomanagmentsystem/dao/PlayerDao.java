package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;

import java.util.List;

public interface PlayerDao {
    void addPlayer(String pname, String pemail, Match match);
    List<Player> getAllPlayers();
    Player getPlayerById(int pid);
}
