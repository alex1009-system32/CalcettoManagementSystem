package org.example.calcettomanagmentsystem.core;

import com.github.javafaker.Faker;
import org.example.calcettomanagmentsystem.dao.TeamDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLitePlayerDao;
import org.example.calcettomanagmentsystem.dao.impl.SQLiteTeamDao;
import org.example.calcettomanagmentsystem.exeptions.DataAccessException;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.management.ServiceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.stream.Gatherers;

/**
 * Verantwortet die Team-Erzeugung aus Spielerdaten.
 * <p>
 * Die Idee ist, dass Team-Zuschnitt und Persistenz in einer Stelle liegen,
 * damit Turnierlogik nicht mit Gruppierungsdetails belastet wird.
 * </p>
 *
 * @see SQLiteTeamDao
 */
public class TeamMaker {

    public boolean makeTeams(Tournament tournament) {
        String teamName;
        Team team;
        Faker faker = new Faker();

        List<List<Player>> teams = partitionTeams(ServiceManager.getPlayerService().findAllOfTournament(tournament),
                                                  tournament.maxTeamSize());

        for (List<Player> teamList : teams) {
            teamName = faker.funnyName().name();
            team = ServiceManager.getTeamService().save(teamName);

            for (Player player : teamList) {
                ServiceManager.getTeamService().save(player, team);
                System.out.println(teamList);
            }
        }

        return true;
    }

    /**
     * Teilt Spieler in Gruppen der gewünschten Größe.
     *
     * @param players  Liste der Spieler, die verteilt werden sollen
     * @param teamSize Zielgröße je Team
     * @return gruppierte Spielerlisten als Team-Kandidaten
     */
    @NotNull
    private @Unmodifiable List<List<Player>> partitionTeams(@NotNull List<Player> players, int teamSize) {
        return players.stream().gather(Gatherers.windowFixed(teamSize)).toList();
    }
}
