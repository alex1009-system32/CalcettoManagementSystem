package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.repo.MatchRepository;
import org.example.calcettomanagmentsystem.service.repo.TournamentRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MatchService {
    MatchRepository matchRepository;

    public MatchService(TournamentRepository tournamentRepository, MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match save(int round, Tournament tournament) {
        if (round < 0) throw new ValidationException("Match round must be greater than 0.");

        if (tournament.id() < 0) throw new ValidationException("Tournament id must be greater than 0.");

        return matchRepository.save(new Match(round, tournament)).orElseThrow(() -> new  ValidationException("Can't get out of the Database"));
    }

    public boolean addTeam(Team team, Match match) {
        if (team.id() < 0) throw new ValidationException("Team id must be greater than 0.");
        if (match.id() < 0) throw new ValidationException("Match id must be greater than 0.");

        if(match.teamResults().containsKey(team.id())) throw new ValidationException("Team already exists");

        return matchRepository.addTeam(team, match);
    }

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public List<Match> findMatchesByTournament(@NotNull Tournament tournament) {
        return matchRepository.findMatchesByTournament(tournament);
    }

    public List<Match> findMatchesByTournamentInCurrentRound(@NotNull Tournament tournament) {
        return matchRepository.findMatchesByTournament(tournament, tournament.currentRound());
    }

}
