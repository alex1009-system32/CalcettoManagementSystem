package org.example.calcettomanagmentsystem.core;

import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Gatherers;

public class TeamShuffler {
    /**
     * Erzeugt zufällige Team-Paare für eine Runde.
     *
     * @param teams Teams, die für Paarungen berücksichtigt werden
     * @return Liste von Team-Paaren, ggf. mit einem Einzelteam
     */
    @Unmodifiable
    @NotNull
    public List<List<Team>> shuffleTeamList(List<Team> teams, int teamSize) {
        Collections.shuffle(teams);
        return teams.stream().gather(Gatherers.windowFixed(teamSize)).toList();
    }
}
