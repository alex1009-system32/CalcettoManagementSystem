package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.service.interfaces.Repository;

import java.util.List;

public class TeamRepository implements Repository<Team> {
    TeamDao teamDao;

    public TeamRepository(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public Team save(Team obj) {
        return teamDao.addTeam(obj.getTeamName());
    }

    @Override
    public boolean delete(Team obj) {
        return teamDao.deleteTeam(obj);
    }

    @Override
    public List<Team> findAll() {
        return teamDao.getAllTeams();
    }

    @Override
    public Team findById(int id) {
        return teamDao.getTeamById(id);
    }
}
