package org.example.calcettomanagmentsystem.service.repo;

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
 * @author Senior Developer
 */
public class TeamRepository implements org.example.calcettomanagmentsystem.service.interfaces.TeamRepository {
    /** The underlying DAO for database operations. */
    private TeamDao teamDao;

    /**
     * Constructs a new TeamRepository with the specified DAO.
     *
     * @param teamDao The DAO to use for persistence.
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
