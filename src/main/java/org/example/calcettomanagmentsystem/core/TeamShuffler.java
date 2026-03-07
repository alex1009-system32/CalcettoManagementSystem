package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Gatherers;

public class TeamShuffler {
    
    @Unmodifiable
    @NotNull
    public List<List<Team>> shuffleTeamList(List<Team> teams, int teamSize) {
        List<Team> mutableTeams = new ArrayList<>(teams);
        Collections.shuffle(mutableTeams);
        return mutableTeams.stream().gather(Gatherers.windowFixed(teamSize)).toList();
    }
}
