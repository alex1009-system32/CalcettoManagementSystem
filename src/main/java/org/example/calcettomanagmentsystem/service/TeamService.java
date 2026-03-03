package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.service.repo.TeamRepository;

public class TeamService {
    TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
}
