package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.service.interfaces.Repository;

import java.util.List;
import java.util.Optional;

public class TeamRepository implements Repository<Team> {
    private TeamDao teamDao;

    public TeamRepository(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public Optional<Team> save(Team obj) {
        return teamDao.save(obj);
    }

    @Override
    public boolean delete(Team obj) {
        return teamDao.delete(obj);
    }

    @Override
    public List<Team> findAll() {
        return teamDao.findAll();
    }

    @Override
    public Optional<Team> findById(int id) {
        return teamDao.findById(id);
    }
}
