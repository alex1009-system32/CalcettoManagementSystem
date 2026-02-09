package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Player;

import java.util.List;

public interface PlayerDao {
    void addPlayer(String pname, String pemail);
    List<Player> getAllPlayers();
    Player getPlayerById(int pid);
}
