package org.example.calcettomanagmentsystem.service.interfaces;

import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface TeamRepository extends Repository<Team> {
    Team addPlayer(Player player, Team team);
    List<Team> findByTournament(Tournament tournament);
}
