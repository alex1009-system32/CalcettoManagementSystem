package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;

import java.util.List;
import java.util.Optional;

public class TeamRepository implements org.example.calcettomanagmentsystem.service.interfaces.TeamRepository {
    private TeamDao teamDao;

    public TeamRepository(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public Optional<Team> save(Team obj) {
        return teamDao.save(obj);
    }

    @Override
    public Team addPlayer(Player player, Team team) {
        return teamDao.addPlayer(team, player);
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
