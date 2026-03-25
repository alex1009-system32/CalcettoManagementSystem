package org.example.calcettomanagmentsystem.service.management;

import org.example.calcettomanagmentsystem.core.connection.interfaces.DataBaseSource;
import org.example.calcettomanagmentsystem.core.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamMaker;
import org.example.calcettomanagmentsystem.core.TeamShuffler;
import org.example.calcettomanagmentsystem.core.WinnerExtractor;
import org.example.calcettomanagmentsystem.core.dao.MatchDao;
import org.example.calcettomanagmentsystem.core.dao.PlayerDao;
import org.example.calcettomanagmentsystem.core.dao.TeamDao;
import org.example.calcettomanagmentsystem.core.dao.TournamentDao;
import org.example.calcettomanagmentsystem.core.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.core.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.core.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.core.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.core.model.Tournament;
import org.example.calcettomanagmentsystem.service.*;
import org.example.calcettomanagmentsystem.service.repo.*;

/**
 * Centralized manager for application services and global state.
 * <p>
 * This class uses the Service Locator pattern to provide access to various
 * domain services and manages the currently active {@link Tournament}.
 * </p>
 *
 * @author Senior Developer
 */
public class ServiceManager {
    /** Global data source for all DAOs. */
    private static final DataBaseSource DATA_BASE_SOURCE = new SQLiteDB();

    /** DAOs for entity persistence. */
    private static final TournamentDao TOURNAMENT_DAO = new SQLiteTournamentDao(DATA_BASE_SOURCE);
    private static final MatchDao MATCH_DAO = new SQLiteMatchDao(DATA_BASE_SOURCE);
    private static final TeamDao TEAM_DAO = new SQLiteTeamDao(DATA_BASE_SOURCE);
    private static final PlayerDao PLAYER_DAO = new SQLitePlayerDao(DATA_BASE_SOURCE);

    /** Core logic components for tournament processing. */
    private static final TeamShuffler TEAM_SHUFFLER = new TeamShuffler();
    private static final WinnerExtractor WINNER_EXTRACTOR = new WinnerExtractor();

    private static final TeamMaker TEAM_MAKER = new TeamMaker();
    private static final MatchMaker MATCH_MAKER = new MatchMaker(TEAM_SHUFFLER, WINNER_EXTRACTOR);

    /** Repositories wrapping DAOs with service-specific logic. */
    private static final TournamentRepository TOURNAMENT_REPOSITORY = new TournamentRepository(TOURNAMENT_DAO);
    private static final MatchRepository MATCH_REPOSITORY = new MatchRepository(MATCH_DAO);
    private static final TeamRepository TEAM_REPOSITORY = new TeamRepository(TEAM_DAO);
    private static final PlayerRepository PLAYER_REPOSITORY = new PlayerRepository(PLAYER_DAO);
    private static final MakerRepository MAKER_REPOSITORY =
            new MakerRepository(TOURNAMENT_DAO, MATCH_DAO, TEAM_DAO, DATA_BASE_SOURCE, TEAM_MAKER, MATCH_MAKER);

    /** Publicly accessible services. */
    private static final TournamentService TOURNAMENT_SERVICE =
            new TournamentService(TOURNAMENT_REPOSITORY, MAKER_REPOSITORY);
    private static final MatchService MATCH_SERVICE = new MatchService(MATCH_REPOSITORY);
    private static final TeamService TEAM_SERVICE = new TeamService(TEAM_REPOSITORY);
    private static final PlayerService PLAYER_SERVICE = new PlayerService(PLAYER_REPOSITORY);
    private static final MakerService MAKER_SERVICE = new MakerService(MAKER_REPOSITORY);

    /** The currently selected tournament context. */
    private static Tournament tournament = null;

    /** Private constructor to enforce static usage. */
    private ServiceManager() {
    }

    /**
     * Sets the active tournament for the application session.
     *
     * @param tournament The {@link Tournament} to set as active.
     */
    public static void setTournament(Tournament tournament) {
        ServiceManager.tournament = tournament;
    }

    /**
     * Provides access to the shared data source.
     *
     * @return The singleton {@link DataBaseSource} instance.
     */
    public static DataBaseSource getDataBaseSource() {
        return DATA_BASE_SOURCE;
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
