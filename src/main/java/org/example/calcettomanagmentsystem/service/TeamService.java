package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.repo.TeamRepository;

import java.util.List;

public class TeamService {
    TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team save(String name) {
        return teamRepository.save(new Team(name))
                .orElseThrow(() -> new ValidationException("Can't get out of the Database"));
    }

    public Team save(Player player, Team team) {
        return teamRepository.addPlayer(player, team);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public List<Team> findAllByTournament(Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("Tournament id cannot be less than 0");

        return teamRepository.findByTournament(tournament);
    }
}
