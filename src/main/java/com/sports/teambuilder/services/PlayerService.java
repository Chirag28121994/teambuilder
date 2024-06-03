package com.sports.teambuilder.services;

import com.sports.teambuilder.dao.PlayerRepository;
import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PlayerService {

    @Autowired
    public PlayerRepository playerRepository;

    public List<Player> getAllPlayersFromDB() {
        log.info("Get List of all the players from db");
        return playerRepository.findAll();
    }

    public Optional<List<Player>> findByName(String name) {
        return playerRepository.findPlayersByName(name);
    }

    public Optional<List<Player>> findByPrimarySport(String sport) {
        return playerRepository.findAllPlayersBySport(SportsCategory.valueOf(sport));
    }

    public Player savePlayerData(Player player) {
        if (player.getMobileNumber() == null || player.getMobileNumber().isEmpty()) {
            log.error("User need to pass the mobile Mobile Number in the request body");
            throw new IllegalArgumentException("Mobile number can't be empty or null");
        }
        if (player.getName() == null || player.getName().isEmpty()) {
            log.error("User need to pass the mobile Mobile Number in the request body");
            throw new IllegalArgumentException("Mobile number can't be empty or null");
        }
        Optional<Player> existingPlayer = playerRepository.findByMobileNumber(player.getMobileNumber());
        if (existingPlayer.isPresent()) {
            log.error("Player with prvided mobile number is already present ");
            return existingPlayer.get();
        }
        return playerRepository.save(player);
    }

}
