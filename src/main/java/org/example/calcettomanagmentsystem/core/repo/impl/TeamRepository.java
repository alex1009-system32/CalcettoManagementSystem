package org.example.calcettomanagmentsystem.core.repo.impl;

import org.example.calcettomanagmentsystem.core.dao.TeamDao;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation for team data access.
 * <p>
 * This class acts as a mediator between the {@link TeamService} and the {@link TeamDao}.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class TeamRepository implements org.example.calcettomanagmentsystem.core.repo.TeamRepository {
    /** The underlying DAO for team and roster database operations. */
    private final TeamDao teamDao;

    /**
     * Constructs a new TeamRepository with the required DAO for data access.
     *
     * @param teamDao The DAO used to handle team persistence.
     */
    public TeamRepository(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Team> save(Team obj) {
        return teamDao.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Team obj) {
        return teamDao.delete(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> findAll() {
        return teamDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Team> findById(int id) {
        return teamDao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Team addPlayer(Player player, Team team) {
        return teamDao.addPlayer(team, player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> findByTournament(Tournament tournament) {
        return teamDao.findByTournament(tournament);
    }
}
