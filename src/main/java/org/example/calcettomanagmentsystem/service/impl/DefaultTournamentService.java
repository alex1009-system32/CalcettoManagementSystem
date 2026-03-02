package org.example.calcettomanagmentsystem.service.impl;

import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamMaker;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.TournamentService;

public class DefaultTournamentService implements TournamentService {
    private Tournament tournament;

    public DefaultTournamentService(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public void generateTeams() {
        new TeamMaker().makeTeams(this.tournament);
    }

    @Override
    public void generatePreRoundMatches() {
        new MatchMaker().makePreRounds(this.tournament);
    }

    @Override
    public void generateRoundMatches() {
        new MatchMaker().makeMatchesForRound(this.tournament);
    }
}