package com.sports.teambuilder.controller;

import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.Player;
import com.sports.teambuilder.services.PlayerService;
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

    @GetMapping("allPlayers")
    public List<Player> getAllPlayers() {
        log.info("Request list of all the players");
        return playerService.getAllPlayersFromDB();
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

}
