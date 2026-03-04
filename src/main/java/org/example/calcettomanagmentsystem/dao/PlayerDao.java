package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PlayerDao extends GeneralDao<Player> {
    List<Player> getAllPlayersFromTournament(@NotNull Tournament tournament);
}
