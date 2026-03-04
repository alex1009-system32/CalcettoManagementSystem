package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.Repository;

import java.util.List;
import java.util.Optional;

public class TournamentRepository implements Repository<Tournament> {
    private TournamentDao tournamentDao;

    public TournamentRepository(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    @Override
    public Optional<Tournament> save(Tournament tournament) {
        return tournamentDao.save(tournament);
    }

    @Override
    public boolean delete(Tournament tournament) {
        return tournamentDao.delete(tournament);
    }

    @Override
    public List<Tournament> findAll() {
        return tournamentDao.findAll();
    }

    @Override
    public Optional<Tournament> findById(int id) {
        return tournamentDao.findById(id);
    }
}
