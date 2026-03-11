package org.example.calcettomanagmentsystem.dao;

import org.example.calcettomanagmentsystem.model.Tournament;

import java.util.Optional;

public interface TournamentDao extends GeneralDao<Tournament> {
    Tournament increaseRound(Tournament tournament);
}
