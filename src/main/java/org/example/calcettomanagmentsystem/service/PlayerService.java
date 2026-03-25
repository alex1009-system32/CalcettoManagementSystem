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
        if (name.isEmpty()) throw new ValidationException("Player name cannot be empty");
        if (name.matches(".* .*")) throw new ValidationException("Player name cannot contain spaces");
        if (name.matches(".*[!\"#$%...].*"))
            throw new ValidationException("Player name cannot contain Special Characters");

        if (email.isEmpty()) throw new ValidationException("Player name cannot be empty");
        if (email.matches(".* .*")) throw new ValidationException("Player name cannot contain spaces");
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            throw new ValidationException("Player name cannot contain Special Characters");

        return playerRepository.save(new Player(name, email, tournament)).orElse(null);
    }

    public boolean delete(@NotNull Player player) {
        if (player.id() < 0) throw new ValidationException("Player id cannot be less than 0");

        return playerRepository.delete(player);
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public List<Player> findAllByTournament(Tournament tournament) {
        if (tournament.id() < 0) throw new ValidationException("Tournament id cannot be less than 0");

        return playerRepository.findAll()
                .stream()
                .filter(player -> player.tournament().id() == tournament.id())
                .toList();
    }
}
