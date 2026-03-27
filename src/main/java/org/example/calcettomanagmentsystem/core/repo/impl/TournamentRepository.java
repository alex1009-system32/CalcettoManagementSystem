package org.example.calcettomanagmentsystem.core.repo.impl;

import org.example.calcettomanagmentsystem.core.dao.TournamentDao;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation for tournament data access.
 * <p>
 * This class acts as a mediator between the {@link TournamentService} and the {@link TournamentDao}.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.0
 */
public class TournamentRepository implements org.example.calcettomanagmentsystem.core.repo.TournamentRepository {
    /** The underlying DAO for database operations. */
    private TournamentDao tournamentDao;

    /**
     * Constructs a new TournamentRepository with the specified DAO.
     *
     * @param tournamentDao The DAO to use for persistence.
     */
    public TournamentRepository(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tournament> save(Tournament obj) {
        return tournamentDao.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Tournament obj) {
        return tournamentDao.delete(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tournament> findAll() {
        return tournamentDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tournament> findById(int id) {
        return tournamentDao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tournament> increaseRound(Tournament tournament) {
        return Optional.ofNullable(tournamentDao.increaseRound(tournament));
    }
}
