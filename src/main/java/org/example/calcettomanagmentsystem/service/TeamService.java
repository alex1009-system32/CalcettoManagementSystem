package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.service.repo.TeamRepository;

import java.util.List;

public class TeamService {
    TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}
