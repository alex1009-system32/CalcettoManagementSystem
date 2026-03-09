package org.example.calcettomanagmentsystem.service.repo;

import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamMaker;
import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MakerRepository implements org.example.calcettomanagmentsystem.service.interfaces.MakerRepository {
    private TournamentDao tournamentDao;
    private MatchDao matchDao;
    private TeamDao teamDao;

    private TeamMaker teamMaker;
    private MatchMaker matchMaker;

    public MakerRepository(TeamMaker teamMaker,
                           MatchMaker matchMaker,
                           TeamDao teamDao,
                           MatchDao matchDao,
                           TournamentDao tournamentDao) {
        this.teamMaker = teamMaker;
        this.matchMaker = matchMaker;
        this.tournamentDao = tournamentDao;
        this.teamDao = teamDao;
        this.matchDao = matchDao;
    }

    @Override
    public List<Team> generateTeams(List<Player> players, int teamSize) {
        List<Team> finalTeams = new ArrayList<>();
        List<Team> teams = teamMaker.makeTeams(players, teamSize);

        try (Connection connection = SQLiteDB.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Team team : teams) {
                    Team finalTeam =
                            teamDao.save(team).orElseThrow(() -> new DataAccessException("Team could not be saved"));
                    for (Player player : team.players()) {
                        finalTeam = teamDao.addPlayer(finalTeam, player);
                    }
                    finalTeams.add(finalTeam);
                }
                connection.commit();
                return finalTeams;
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Something went wrong while trying to save the teams", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Something went wrong while trying to save the teams", e);
        }
    }

    private List<Match> generateMatches(Tournament tournament, List<Match> newMatches) {
        List<Match> finalMatches = new ArrayList<>();
        try (Connection connection = SQLiteDB.getConnection()) {
            connection.setAutoCommit(false);
            for (int i = 0; i <= tournament.preRound(); i++) {
                tournamentDao.increaseRound(tournament);
            }
            try {
                for (Match match : newMatches) {
                    Match finalMatch =
                            matchDao.save(match).orElseThrow(() -> new DataAccessException("Match could not be saved"));
                    for (Team team : match.teamResults().keySet()) {
                        finalMatch = matchDao.registerTeam(team, finalMatch);
                    }
                    finalMatches.add(finalMatch);
                }
                connection.commit();
                return finalMatches;
            } catch (SQLException e) {
                connection.rollback();
                throw new DataAccessException("Something went wrong while trying to save the matches", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Something went wrong while trying to save the matches", e);
        }
    }

    @Override
    public List<Match> generatePreRoundMatches(Tournament tournament, List<Team> teams) {
        return generateMatches(tournament, matchMaker.makePreRounds(tournament, teams));
    }

    @Override
    public List<Match> gerateRoundMatchesAfterPreRounds(Tournament tournament, List<Match> matches) {
        return generateMatches(tournament, matchMaker.makeMatchesForRoundAfterPreRounds(tournament, matches));
    }

    @Override
    public List<Match> gerateRoundMatches(Tournament tournament, List<Match> matches) {
        return generateMatches(tournament, matchMaker.makeMatchesForRound(tournament, matches));
    }
}