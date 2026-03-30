package org.example.calcettomanagmentsystem.core.repo.impl;

import org.example.calcettomanagmentsystem.core.dao.PlayerDao;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.repo.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation for player data access.
 * <p>
 * This class acts as a mediator between the {@link PlayerService} and the {@link PlayerDao}.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class PlayerRepository implements Repository<Player> {
    /** The underlying DAO for player-related database operations. */
    private final PlayerDao playerDao;

    /**
     * Constructs a new PlayerRepository with the required DAO for data access.
     *
     * @param playerDao The DAO used to handle player persistence.
     */
    public PlayerRepository(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> save(Player obj) {
        return playerDao.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Player obj) {
        return playerDao.delete(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> findAll() {
        return playerDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> findById(int id) {
        return playerDao.findById(id);
    }
}
