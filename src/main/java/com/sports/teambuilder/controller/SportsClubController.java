package com.sports.teambuilder.controller;

import com.sports.teambuilder.models.SportsClub;
import com.sports.teambuilder.services.SportsClubService;
import dto.PlayerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("sportsClub")
public class SportsClubController {

    @Autowired
    private SportsClubService sportsClubService;

    @GetMapping("allClubs")
    public List<SportsClub> getAllClubs() {
        log.info("Request list of all the players");
        return sportsClubService.getAllPlayersFromDB();
    }

    @GetMapping("/name/{name}")
    public Optional<List<SportsClub>> getClubByName(@PathVariable String name) {
        return sportsClubService.findByName(name);
    }

    @GetMapping("/name/{name}/playerList")
    public Optional<List<PlayerDto>> getListOfPlayersAssociateWithClub(@PathVariable String clubName) {
        Optional<List<PlayerDto>> playerDtos;
        try {
            playerDtos = sportsClubService.getAllPlayersOfGivenClub(clubName);
            return playerDtos;
        } catch (IllegalArgumentException e) {
            e.fillInStackTrace();
            return Optional.empty();
        }
    }

    @GetMapping("/sport/{sport}")
    public Optional<List<SportsClub>> getClubBySport(@PathVariable String sport) {
        return sportsClubService.findByPrimarySport(sport.toUpperCase());
    }

    @PostMapping("addClub")
    public SportsClub addSportsClub(@RequestBody SportsClub sportsClub) {
        return sportsClubService.saveClubData(sportsClub);
    }


}
