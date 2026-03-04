package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public class MatchRepository implements org.example.calcettomanagmentsystem.service.interfaces.MatchRepository {
    private MatchDao matchDao;

    public MatchRepository(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    @Override
    public Match save(Match obj) {
        return matchDao.save(obj);
    }

    @Override
    public boolean delete(Match obj) {
        return delete(obj);
    }

    @Override
    public List<Match> findAll() {
        return matchDao.findAll();
    }

    @Override
    public Match findById(int id) {
        return matchDao.findById(id);
    }

    @Override
    public List<Match> findMatchesByTournament(Tournament tournament) {
        return matchDao.findMatchesByTournament(tournament);
    }

    @Override
    public List<Match> findMatchesByTournament(Tournament tournament, int round) {
        return matchDao.findMatchesByTournament(tournament, round);
    }
}
