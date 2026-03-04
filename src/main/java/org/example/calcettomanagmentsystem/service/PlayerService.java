package org.example.calcettomanagmentsystem.service;

import org.example.calcettomanagmentsystem.exeptions.ValidationException;
import org.example.calcettomanagmentsystem.model.Player;
import org.example.calcettomanagmentsystem.model.Tournament;
import org.example.calcettomanagmentsystem.service.repo.PlayerRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerService {
    PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player create(@NotNull String name, @NotNull String email, @NotNull Tournament tournament) {
        if (name == "") throw new ValidationException("Player name cannot be empty");
        if (name.matches(".* .*")) throw new ValidationException("Player name cannot contain spaces");
        if (name.matches(".*[!\"#$%...].*"))
            throw new ValidationException("Player name cannot contain Special Characters");

        if (email == "") throw new ValidationException("Player name cannot be empty");
        if (email.matches(".* .*")) throw new ValidationException("Player name cannot contain spaces");
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            throw new ValidationException("Player name cannot contain Special Characters"); // Note is not configed right

        return playerRepository.save(new Player(name, email, tournament));
    }

    public boolean delete(@NotNull Player player) {
        if (player.pid() < 0) throw new ValidationException("Player pid cannot be less than 0");

        return playerRepository.delete(player);
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public List<Player> findAllOfTournament(Tournament tournament) {
        return playerRepository.findAll().stream().filter(player -> player.tournament().getTid() == tournament.getTid()).toList();
    }
}
