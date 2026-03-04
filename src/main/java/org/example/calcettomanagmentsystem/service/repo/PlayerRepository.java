package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.PlayerDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.service.interfaces.Repository;

import java.util.List;
import java.util.Optional;

public class PlayerRepository implements Repository<Player> {
    private PlayerDao playerDao;

    public PlayerRepository(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Optional<Player> save(Player obj) {
        return playerDao.save(obj);
    }

    @Override
    public boolean delete(Player obj) {
        return playerDao.delete(obj);
    }

    @Override
    public List<Player> findAll() {
        return playerDao.findAll();
    }

    @Override
    public Optional<Player> findById(int id) {
        return playerDao.findById(id);
    }
}
