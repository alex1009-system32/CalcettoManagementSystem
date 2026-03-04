package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.old.TournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.Repository;

import java.util.List;

public class TournamentRepository implements Repository<Tournament> {
    TournamentDao tournamentDao;

    public TournamentRepository(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    @Override
    public Tournament save(Tournament tournament) {
        return tournamentDao.addTournament(tournament);
    }

    @Override
    public boolean delete(Tournament tournament) {
        return tournamentDao.deleteTournament(tournament);
    }

    @Override
    public List<Tournament> findAll() {
        return tournamentDao.getAllTournaments();
    }

    @Override
    public Tournament findById(int id) {
        return tournamentDao.getTournamentById(id);
    }
}
