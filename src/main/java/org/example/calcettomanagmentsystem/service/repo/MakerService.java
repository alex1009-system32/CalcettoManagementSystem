package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamMaker;
import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.MakerRepository;

public class MakerService implements MakerRepository {
    private TeamMaker teamMaker;
    private MatchMaker matchMaker;

    public MakerService(TeamMaker teamMaker, MatchMaker matchMaker) {
        this.teamMaker = teamMaker;
        this.matchMaker = matchMaker;
    }

    @Override
    public boolean generateTeams(Tournament tournament) {
        return teamMaker.makeTeams(tournament);
    }

    @Override
    public boolean generatePreRoundMatches(Tournament tournament) {
        return matchMaker.makePreRounds(tournament);
    }

    @Override
    public boolean generateRoundMatches(Tournament tournament) {
        return matchMaker.makeMatchesForRound(tournament);
    }
}