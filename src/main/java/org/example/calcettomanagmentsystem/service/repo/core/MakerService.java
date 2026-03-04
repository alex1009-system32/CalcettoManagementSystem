package org.example.calcettomanagmentsystem.service.repo.core;

import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamMaker;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.interfaces.MakerRepository;

public class MakerService implements MakerRepository {
    TeamMaker teamMaker;
    MatchMaker matchMaker;

    public MakerService(TeamMaker teamMaker, MatchMaker matchMaker) {
        this.teamMaker = teamMaker;
        this.matchMaker = matchMaker;
    }

    @Override
    public boolean generateTeams(Tournament tournament) {
        return new TeamMaker().makeTeams(tournament);
    }

    @Override
    public boolean generatePreRoundMatches(Tournament tournament) {
        return new MatchMaker().makePreRounds(tournament);
    }

    @Override
    public boolean generateRoundMatches(Tournament tournament) {
        return new MatchMaker().makeMatchesForRound(tournament);
    }
}