package com.sports.teambuilder.services;

import com.sports.teambuilder.repository.mongo.PlayerRepository;
import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.Player;
import com.sports.teambuilder.dto.PlayerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

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
        Optional<Player> existingPlayer = Optional.empty();
        try {
            existingPlayer = playerRepository.findByMobileNumber(player.getMobileNumber());
        } catch (NullPointerException e) {
        }
        if (existingPlayer.isPresent()) {
            log.error("Player with mobile number {} is already present ", player.getMobileNumber());
            return existingPlayer.get();
        }
        return playerRepository.save(player);
    }

    public void deletePlayer(PlayerDto playerDto) {
        playerRepository.deleteByMobileNumber(playerDto.getMobileNumber());
    }

    public void deletePlayerByMobileNumber(String mobileNumber) {
        playerRepository.deleteByMobileNumber(mobileNumber);
    }

    public Player updatePlayerInformation(Player player, String mobileNumber) {
        Optional<Player> existingPlayer = playerRepository.findByMobileNumber(mobileNumber);
        if (existingPlayer.isEmpty()) return new Player();
        if (player.getName() != null) {
            existingPlayer.get().setName(player.getName());
        }
        if (player.getIsActive() != null) existingPlayer.get().setIsActive(player.getIsActive());
        if (player.getHomeGround() != null) existingPlayer.get().setHomeGround(player.getHomeGround());
        if (player.getPrimarySport() != null) existingPlayer.get().setHomeGround(player.getPrimarySport());

        return playerRepository.save(existingPlayer.get());
    }
}
