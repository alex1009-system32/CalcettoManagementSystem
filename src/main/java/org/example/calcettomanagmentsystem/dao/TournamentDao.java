package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Tournament;

public interface TournamentDao extends GeneralDao<Tournament> {
    Tournament increaseRound(Tournament tournament);
}
