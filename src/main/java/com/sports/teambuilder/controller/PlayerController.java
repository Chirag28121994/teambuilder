package com.sports.teambuilder.controller;

import com.sports.teambuilder.models.Player;
import com.sports.teambuilder.services.PlayerService;
import com.sports.teambuilder.dto.PlayerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @DeleteMapping("/deletePlayer")
    public void deletePlayer(@RequestBody PlayerDto playerDto) {
        playerService.deletePlayer(playerDto);
    }

    @DeleteMapping("/deletePlayer/{mobileNumber}")
    public void deletePlayerByMobileNumber(@PathVariable String mobileNumber) {
        playerService.deletePlayerByMobileNumber(mobileNumber);
    }

    @GetMapping("/name/{name}")
    public Optional<List<Player>> getPlayersByName(@PathVariable String name) {
        return playerService.findByName(name);
    }

    @GetMapping("/sport/{sport}")
    public Optional<List<Player>> getPlayersBySport(@PathVariable String sport) {
        return playerService.findByPrimarySport(sport.toUpperCase());
    }

    @PostMapping("addPlayer")
    public Player addPlayer(@RequestBody Player player) {
        return playerService.savePlayerData(player);
    }

    @PutMapping("updatePlayer/{mobileNumber}")
    public Player updatePlayerDetails(@RequestBody Player player, @PathVariable String mobileNumber) {
        return playerService.updatePlayerInformation(player, mobileNumber);
    }

}
