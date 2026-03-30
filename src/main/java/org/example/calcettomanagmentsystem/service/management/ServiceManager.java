package org.example.calcettomanagmentsystem.service.management;

import org.example.calcettomanagmentsystem.core.*;
import org.example.calcettomanagmentsystem.core.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.core.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.core.dao.MatchDao;
import org.example.calcettomanagmentsystem.core.dao.PlayerDao;
import org.example.calcettomanagmentsystem.core.dao.TeamDao;
import org.example.calcettomanagmentsystem.core.dao.TournamentDao;
import org.example.calcettomanagmentsystem.core.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.core.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.core.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.core.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.core.repo.impl.*;
import org.example.calcettomanagmentsystem.service.*;

/**
 * Centralized manager for application services and global state.
 * <p>
 * This class uses the Service Locator pattern to provide access to various
 * domain services and manages the currently active {@link Tournament}.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public class ServiceManager {
    /** Global data source providing database connections for all repository and DAO operations. */
    private static final DataBaseSource DATA_BASE_SOURCE = new SQLiteDB();

    /** DAO for managing tournament persistence and round state. */
    private static final TournamentDao TOURNAMENT_DAO = new SQLiteTournamentDao(DATA_BASE_SOURCE);
    /** DAO for managing match records and scoring data. */
    private static final MatchDao MATCH_DAO = new SQLiteMatchDao(DATA_BASE_SOURCE);
    /** DAO for managing team entities and player rosters. */
    private static final TeamDao TEAM_DAO = new SQLiteTeamDao(DATA_BASE_SOURCE);
    /** DAO for managing player profiles and contact information. */
    private static final PlayerDao PLAYER_DAO = new SQLitePlayerDao(DATA_BASE_SOURCE);

    /** Component used for randomizing team ordering during tournament scheduling. */
    private static final TeamShuffler TEAM_SHUFFLER = new TeamShuffler();
    /** Component used for calculating points and identifying round winners. */
    private static final WinnerExtractor WINNER_EXTRACTOR = new WinnerExtractor();

    /** Component used for organizing players into balanced teams. */
    private static final TeamMaker TEAM_MAKER = new TeamMaker();
    /** Component used for generating complete match schedules for all tournament phases. */
    private static final MatchMaker MATCH_MAKER = new MatchMaker(TEAM_SHUFFLER, WINNER_EXTRACTOR);

    /** Repository wrapping the tournament DAO with high-level data access logic. */
    private static final TournamentRepository TOURNAMENT_REPOSITORY = new TournamentRepository(TOURNAMENT_DAO);
    /** Repository wrapping the match DAO with high-level data access logic. */
    private static final MatchRepository MATCH_REPOSITORY = new MatchRepository(MATCH_DAO);
    /** Repository wrapping the team DAO with high-level data access logic. */
    private static final TeamRepository TEAM_REPOSITORY = new TeamRepository(TEAM_DAO);
    /** Repository wrapping the player DAO with high-level data access logic. */
    private static final PlayerRepository PLAYER_REPOSITORY = new PlayerRepository(PLAYER_DAO);
    /** Repository for complex generation operations involving teams and matches. */
    private static final MakerRepository MAKER_REPOSITORY =
            new MakerRepository(TOURNAMENT_DAO, MATCH_DAO, TEAM_DAO, DATA_BASE_SOURCE, TEAM_MAKER, MATCH_MAKER);

    /** Singleton service for managing tournament-level business logic. */
    private static final TournamentService TOURNAMENT_SERVICE =
            new TournamentService(TOURNAMENT_REPOSITORY, MAKER_REPOSITORY);
    /** Singleton service for managing match-level business logic. */
    private static final MatchService MATCH_SERVICE = new MatchService(MATCH_REPOSITORY);
    /** Singleton service for managing team-level business logic. */
    private static final TeamService TEAM_SERVICE = new TeamService(TEAM_REPOSITORY);
    /** Singleton service for managing player-level business logic. */
    private static final PlayerService PLAYER_SERVICE = new PlayerService(PLAYER_REPOSITORY);
    /** Singleton service for high-level tournament scheduling and round generation. */
    private static final MakerService MAKER_SERVICE = new MakerService(MAKER_REPOSITORY);

    /** The currently selected tournament context used across multiple views in the UI. */
    private static Tournament tournament = null;

    /**
     * Private constructor to prevent instantiation of this utility/service-locator class.
     */
    private ServiceManager() {
    }

    /**
     * Initializes the underlying database schema.
     * <p>
     * This method delegates to the {@link DataBaseSource#init()} method and should be 
     * called once during application startup.
     * </p>
     */
    public static void initDB() {
        DATA_BASE_SOURCE.init();
    }

    /**
     * Synchronizes the current tournament state with the database.
     * <p>
     * This method re-fetches the active tournament's data to ensure that any 
     * round progression or parameter changes are reflected in the UI context.
     * </p>
     */
    public static void updateTournament(){
        try {
            int id = tournament.id();
            tournament = ServiceManager.getTournamentService().findById(id);
        } catch (ValidationException e) {
            // Error handling logic for tournament synchronization failures
        }
    }

    /**
     * Sets the active tournament for the current application session.
     * <p>
     * Changing the tournament context typically triggers updates in the UI to 
     * display relevant matches, teams, and players.
     * </p>
     *
     * @param tournament The {@link Tournament} to set as the active context.
     */
    public static void setTournament(Tournament tournament) {
        ServiceManager.tournament = tournament;
    }

    /**
     * Provides access to the tournament management service.
     *
     * @return The singleton {@link TournamentService} instance.
     */
    public static TournamentService getTournamentService() {
        return TOURNAMENT_SERVICE;
    }

    /**
     * Provides access to the match management service.
     *
     * @return The singleton {@link MatchService} instance.
     */
    public static MatchService getMatchService() {
        return MATCH_SERVICE;
    }

    /**
     * Provides access to the team management service.
     *
     * @return The singleton {@link TeamService} instance.
     */
    public static TeamService getTeamService() {
        return TEAM_SERVICE;
    }

    /**
     * Provides access to the player management service.
     *
     * @return The singleton {@link PlayerService} instance.
     */
    public static PlayerService getPlayerService() {
        return PLAYER_SERVICE;
    }

    /**
     * Provides access to the tournament schedule generator service.
     *
     * @return The singleton {@link MakerService} instance.
     */
    public static MakerService getMakerService() {
        return MAKER_SERVICE;
    }

    /**
     * Retrieves the currently active tournament.
     *
     * @return The active {@link Tournament}, or {@code null} if none is selected.
     */
    public static Tournament getTournament() {
        return ServiceManager.tournament;
    }
}
