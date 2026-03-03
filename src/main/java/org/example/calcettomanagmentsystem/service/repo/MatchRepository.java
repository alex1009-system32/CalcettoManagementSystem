package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.MatchServiceRepository;
import org.example.calcettomanagmentsystem.service.interfaces.Repository;

import java.util.List;

public class MatchRepository implements Repository<Match>,
                                        MatchServiceRepository {
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
