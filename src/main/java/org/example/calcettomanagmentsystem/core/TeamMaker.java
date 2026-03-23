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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;


public class TeamMaker {

    public List<Team> makeTeams(List<Player> players, int teamSize) {
        String name;
        Team team;
        Faker faker = new Faker();

        List<Team> returnList = new ArrayList<>();
        List<List<Player>> teams = partitionTeams(players, teamSize);

        for (List<Player> teamList : teams) {
            name = faker.funnyName().name();
            team = new Team(name);
            for (Player player : teamList) {
                team.players().add(player);
            }

            returnList.add(team);
        }

        return returnList;
    }


    @NotNull
    private @Unmodifiable List<List<Player>> partitionTeams(@NotNull List<Player> players, int teamSize) {
        return players.stream().gather(Gatherers.windowFixed(teamSize)).toList();
    }
}
