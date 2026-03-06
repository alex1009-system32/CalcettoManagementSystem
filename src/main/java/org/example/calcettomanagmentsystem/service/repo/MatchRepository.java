package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;
import java.util.Optional;

public class MatchRepository implements org.example.calcettomanagmentsystem.service.interfaces.MatchRepository {
    private MatchDao matchDao;

    public MatchRepository(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    @Override
    public Optional<Match> save(Match obj) {
        return matchDao.save(obj);
    }

    @Override
    public boolean addTeam(Team team, Match match) {
        return matchDao.registerTeam(team, match);
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
    public Optional<Match> findById(int id) {
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
