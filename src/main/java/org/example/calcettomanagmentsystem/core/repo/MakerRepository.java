package org.example.calcettomanagmentsystem.core.repo;

import org.example.calcettomanagmentsystem.core.model.Match;
import org.example.calcettomanagmentsystem.core.model.Player;
import org.example.calcettomanagmentsystem.core.model.Team;
import org.example.calcettomanagmentsystem.core.model.Tournament;

import java.util.List;

/**
 * Interface defining the contract for generating tournament structures.
 * <p>
 * This repository is responsible for the algorithmic creation of teams 
 * and match schedules across different tournament phases.
 * </p>
 *
 * @author Alex Kerschbamer
 * @version 0.1
 */
public interface MakerRepository {
     /**
      * Generates teams from a pool of players.
      *
      * @param players The list of players to organize into teams.
      * @param teamSize The target number of players per team.
      * @return A list of newly created {@link Team} objects.
      */
     List<Team> generateTeams(List<Player> players, int teamSize);

     /**
      * Generates matches for the preliminary rounds of a tournament.
      *
      * @param tournament The tournament context.
      * @param teams The participating teams.
      * @return A list of generated matches.
      */
     List<Match> generatePreRoundMatches(Tournament tournament, List<Team> teams);

     /**
      * Generates elimination matches following the completion of preliminary rounds.
      *
      * @param tournament The tournament context.
      * @param matches The preliminary round matches to analyze for winners.
      * @return A list of new elimination matches.
      */
     List<Match> gerateRoundMatchesAfterPreRounds(Tournament tournament, List<Match> matches);

     /**
      * Generates elimination matches for the next round based on current round results.
      *
      * @param tournament The tournament context.
      * @param matches The current round matches to analyze for winners.
      * @return A list of matches for the subsequent round.
      */
     List<Match> gerateRoundMatches(Tournament tournament, List<Match> matches);
}
