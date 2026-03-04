package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.old.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public class MatchRepository implements org.example.calcettomanagmentsystem.service.interfaces.MatchRepository {
    MatchDao matchDao;

    public MatchRepository(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    @Override
    public Match save(Match obj) {
        return null;
    }

    @Override
    public boolean delete(Match obj) {
        return false;
    }

    @Override
    public List<Match> findAll() {
        return List.of();
    }

    @Override
    public Match findById(int id) {
        return null;
    }

    @Override
    public List<Match> findAllFromTournament(Tournament tournament) {
        return matchDao.getAllMatchesFromTournament(tournament);
    }

    @Override
    public List<Match> findAllFromTournamentOfRound(Tournament tournament, int round) {
        return matchDao.getAllMatchesFromTournamentInRound(tournament, round);
    }
}
