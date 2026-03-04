package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.old.PlayerDao;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.service.interfaces.Repository;

import java.util.List;

public class PlayerRepository implements Repository<Player> {
    PlayerDao playerDao;

    public PlayerRepository(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Player save(Player obj) {
        return playerDao.addPlayer(obj.pname(), obj.pemail(), obj.tournament());
    }

    @Override
    public boolean delete(Player obj) {
        return playerDao.deletePlayer(obj);
    }

    @Override
    public List<Player> findAll() {
        return playerDao.getAllPlayers();
    }

    @Override
    public Player findById(int id) {
        return playerDao.getPlayerById(id);
    }
}
