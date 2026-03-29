package org.example.calcettomanagmentsystem.core.repo.impl;

import org.example.calcettomanagmentsystem.core.dao.MatchDao;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation for match data access.
 * <p>
 * This class acts as a mediator between the {@link MatchService} and the {@link MatchDao},
 * providing a cleaner interface for service-level operations.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class MatchRepository implements org.example.calcettomanagmentsystem.core.repo.MatchRepository {
    /** The underlying DAO for database operations. */
    private MatchDao matchDao;

    /**
     * Constructs a new MatchRepository with the specified DAO.
     *
     * @param matchDao The DAO to use for persistence.
     */
    public MatchRepository(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Match> save(Match obj) {
        return matchDao.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match addTeam(Team team, Match match) {
        return matchDao.registerTeam(team, match);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match addPoints(Team team, Match match, int points) {
        return matchDao.assignPoints(team, points, match);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Match obj) {
        return matchDao.delete(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> findAll() {
        return matchDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Match> findById(int id) {
        return matchDao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> findMatchesByTournament(Tournament tournament) {
        return matchDao.findMatchesByTournament(tournament);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> findMatchesByTournament(Tournament tournament, int round) {
        return matchDao.findMatchesByTournament(tournament, round);
    }
}
