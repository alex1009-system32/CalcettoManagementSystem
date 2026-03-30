package org.example.calcettomanagmentsystem.core.repo.impl;

import org.example.calcettomanagmentsystem.core.DataAccessException;
import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamMaker;
import org.example.calcettomanagmentsystem.core.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.core.dao.MatchDao;
import org.example.calcettomanagmentsystem.core.dao.TeamDao;
import org.example.calcettomanagmentsystem.core.dao.TournamentDao;
import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository implementation for generating tournament structures.
 * <p>
 * This class coordinates the creation of teams and match schedules by leveraging 
 * specialized makers and DAOs, while ensuring transactional integrity for complex 
 * generation operations.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class MakerRepository implements org.example.calcettomanagmentsystem.core.repo.MakerRepository {
    /** DAO for tournament-related operations. */
    private final TournamentDao tournamentDao;
    /** DAO for match-related operations. */
    private final MatchDao matchDao;
    /** DAO for team-related operations. */
    private final TeamDao teamDao;

    /** Data source for managing database connections and transactions. */
    private final DataBaseSource dataBaseSource;

    /** Component for team generation logic. */
    private final TeamMaker teamMaker;
    /** Component for match generation logic. */
    private final MatchMaker matchMaker;

    /**
     * Constructs a new MakerRepository with all required dependencies.
     *
     * @param tournamentDao DAO for tournament state management.
     * @param matchDao DAO for match persistence.
     * @param teamDao DAO for team persistence.
     * @param dataBaseSource Data source for transactional operations.
     * @param teamMaker Helper for organizing players into teams.
     * @param matchMaker Helper for generating match schedules.
     */
    public MakerRepository(TournamentDao tournamentDao,
                           MatchDao matchDao,
                           TeamDao teamDao,
                           DataBaseSource dataBaseSource,
                           TeamMaker teamMaker,
                           MatchMaker matchMaker) {
        this.tournamentDao = tournamentDao;
        this.matchDao = matchDao;
        this.teamDao = teamDao;
        this.dataBaseSource = dataBaseSource;
        this.teamMaker = teamMaker;
        this.matchMaker = matchMaker;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation organizes players into teams using {@link TeamMaker} 
     * and persists each team and its roster in a single transaction.
     * </p>
     */
    @Override
    public List<Team> generateTeams(List<Player> players, int teamSize) {
        List<Team> finalTeams = new ArrayList<>();
        List<Team> teams = teamMaker.makeTeams(players, teamSize);

        try (Connection connection = dataBaseSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Team team : teams) {
                    Team finalTeam = teamDao.save(team)
                                            .orElseThrow(() -> new DataAccessException("Team could not be saved"));
                    for (Player player : team.players()) {
                        finalTeam = teamDao.addPlayer(finalTeam, player);
                    }
                    finalTeams.add(finalTeam);
                }


                System.out.println(connection.isClosed());
                connection.commit();
                return finalTeams;
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DataAccessException("Rollback failed", ex);
                }
                throw new DataAccessException("Error occurred while saving generated teams", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database access error during team generation", e);
        }
    }

    /**
     * Helper method to persist a list of generated matches and update the tournament round.
     * <p>
     * This method ensures that the tournament state and all generated matches are 
     * updated atomically.
     * </p>
     *
     * @param tournament The tournament context.
     * @param newMatches The list of matches to be persisted.
     * @return A list of successfully persisted {@link Match} entities.
     * @throws DataAccessException If database operations fail.
     */
    private List<Match> generateMatches(Tournament tournament, List<Match> newMatches) {
        List<Match> finalMatches = new ArrayList<>();
        try (Connection connection = dataBaseSource.getConnection()) {
            connection.setAutoCommit(false);

            if (tournament.currentRound() < tournament.preRound()) {
                for (int i = 0; i < tournament.preRound(); i++) {
                    tournament = tournamentDao.increaseRound(tournament);
                }
            } else {
                tournament = tournamentDao.increaseRound(tournament);
            }

            try {
                for (Match match : newMatches) {
                    Match finalMatch = matchDao.save(match)
                                               .orElseThrow(() -> new DataAccessException("Match could not be saved"));
                    for (Team team : match.teamResults().keySet()) {
                        finalMatch = matchDao.registerTeam(team, finalMatch);
                    }
                    finalMatches.add(finalMatch);
                }
                connection.commit();
                return finalMatches;
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DataAccessException("Something went wrong while trying to save the teams", ex);
                }
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