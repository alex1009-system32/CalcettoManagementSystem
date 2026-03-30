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
 * @since 1.0
 */
public interface MakerRepository {
     /**
      * Generates teams from a given pool of players based on a target team size.
      * <p>
      * This method uses the internal team-making logic to group players 
      * and persists the resulting teams in the repository.
      * </p>
      *
      * @param players The list of {@link Player} entities to organize into teams.
      * @param teamSize The target number of players per team.
      * @return A {@link List} of newly created and persisted {@link Team} objects.
      */
     List<Team> generateTeams(List<Player> players, int teamSize);

     /**
      * Generates match schedules for the preliminary (round-robin) phase of a tournament.
      *
      * @param tournament The {@link Tournament} context for which matches are generated.
      * @param teams The participating {@link Team}s to be scheduled.
      * @return A {@link List} of generated {@link Match} entities for the preliminary rounds.
      */
     List<Match> generatePreRoundMatches(Tournament tournament, List<Team> teams);

     /**
      * Generates elimination matches following the completion of the preliminary phase.
      * <p>
      * This method analyzes the winners from all preliminary matches and schedules 
      * the first round of the elimination bracket.
      * </p>
      *
      * @param tournament The {@link Tournament} context.
      * @param matches The preliminary round {@link Match} entities to analyze for winners.
      * @return A {@link List} of newly created elimination matches.
      */
     List<Match> gerateRoundMatchesAfterPreRounds(Tournament tournament, List<Match> matches);

     /**
      * Generates matches for the next elimination round based on current round results.
      * <p>
      * This method identifies winners from the provided matches and pairs them 
      * for the subsequent tournament round.
      * </p>
      *
      * @param tournament The {@link Tournament} context.
      * @param matches The current round {@link Match} entities to analyze for winners.
      * @return A {@link List} of matches generated for the next elimination round.
      */
     List<Match> gerateRoundMatches(Tournament tournament, List<Match> matches);
}
