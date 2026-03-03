package org.example.calcettomanagmentsystem.service.interfaces;

import org.example.calcettomanagmentsystem.model.Tournament;

public interface MakerRepository {
    boolean generateTeams(Tournament tournament);
    boolean generatePreRoundMatches(Tournament tournament);
    boolean generateRoundMatches(Tournament tournament);
}
