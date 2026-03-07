package org.example.calcettomanagmentsystem.service.interfaces;

import com.almasb.fxgl.scene3d.Torus;
import org.example.calcettomanagmentsystem.model.Match;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Team;
import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.List;

public interface MakerRepository {
     List<Team> generateTeams(List<Player> players, int teamSize);
     List<Match> generatePreRoundMatches(Tournament tournament, List<Team> teams);
     List<Match> gerateRoundMatchesAfterPreRounds(Tournament tournament, List<Match> matches);
     List<Match> gerateRoundMatches(Tournament tournament, List<Match> matches);
}
