package org.example.calcettomanagmentsystem.service.interfaces;

import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;

public interface TeamRepository extends Repository<Team> {
    Team addPlayer(Player player, Team team);
}
