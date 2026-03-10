package org.example.calcettomanagmentsystem.service.management;

import org.example.calcettomanagmentsystem.connection.DataBaseSource;
import org.example.calcettomanagmentsystem.connection.SQLiteDB;
import org.example.calcettomanagmentsystem.core.MatchMaker;
import org.example.calcettomanagmentsystem.core.TeamMaker;
import org.example.calcettomanagmentsystem.core.TeamShuffler;
import org.example.calcettomanagmentsystem.core.WinnerExtractor;
import org.example.calcettomanagmentsystem.dao.MatchDao;
import org.example.calcettomanagmentsystem.dao.PlayerDao;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.dao.TournamentDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteMatchDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTournamentDao;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.*;
import org.example.calcettomanagmentsystem.service.repo.*;

public class ServiceManager {
    private static final DataBaseSource DATA_BASE_SOURCE = new SQLiteDB();

    private static final TournamentDao TOURNAMENT_DAO = new SQLiteTournamentDao(DATA_BASE_SOURCE);
    private static final MatchDao MATCH_DAO = new SQLiteMatchDao(DATA_BASE_SOURCE);
    private static final TeamDao TEAM_DAO = new SQLiteTeamDao(DATA_BASE_SOURCE);
    private static final PlayerDao PLAYER_DAO = new SQLitePlayerDao(DATA_BASE_SOURCE);

    private static final TeamShuffler TEAM_SHUFFLER = new TeamShuffler();
    private static final WinnerExtractor WINNER_EXTRACTOR = new WinnerExtractor();

    private static final TeamMaker TEAM_MAKER = new TeamMaker();
    private static final MatchMaker MATCH_MAKER = new MatchMaker(TEAM_SHUFFLER, WINNER_EXTRACTOR);

    private static final TournamentRepository TOURNAMENT_REPOSITORY = new TournamentRepository(TOURNAMENT_DAO);
    private static final MatchRepository MATCH_REPOSITORY = new MatchRepository(MATCH_DAO);
    private static final TeamRepository TEAM_REPOSITORY = new TeamRepository(TEAM_DAO);
    private static final PlayerRepository PLAYER_REPOSITORY = new PlayerRepository(PLAYER_DAO);
    private static final MakerRepository MAKER_REPOSITORY =
            new MakerRepository(TOURNAMENT_DAO, MATCH_DAO, TEAM_DAO, DATA_BASE_SOURCE, TEAM_MAKER, MATCH_MAKER);

    private static final TournamentService TOURNAMENT_SERVICE =
            new TournamentService(TOURNAMENT_REPOSITORY, MAKER_REPOSITORY);
    private static final MatchService MATCH_SERVICE = new MatchService(MATCH_REPOSITORY);
    private static final TeamService TEAM_SERVICE = new TeamService(TEAM_REPOSITORY);
    private static final PlayerService PLAYER_SERVICE = new PlayerService(PLAYER_REPOSITORY);
    private static final MakerService MAKER_SERVICE = new MakerService(MAKER_REPOSITORY);

    private static Tournament tournament = null;

    private ServiceManager() {
    }

    public static void setTournament(Tournament tournament) {
        ServiceManager.tournament = tournament;
    }

    public static DataBaseSource getDataBaseSource() {
        return DATA_BASE_SOURCE;
    }

    public static TournamentService getTournamentService() {
        return TOURNAMENT_SERVICE;
    }

    public static MatchService getMatchService() {
        return MATCH_SERVICE;
    }

    public static TeamService getTeamService() {
        return TEAM_SERVICE;
    }

    public static PlayerService getPlayerService() {
        return PLAYER_SERVICE;
    }

    public static MakerService getMakerService() {
        return MAKER_SERVICE;
    }

    public static Tournament getTournament() {
        return ServiceManager.tournament;
    }
}
