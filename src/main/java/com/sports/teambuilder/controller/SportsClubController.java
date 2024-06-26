package com.sports.teambuilder.controller;

import com.sports.teambuilder.models.SportsClub;
import com.sports.teambuilder.services.SportsClubService;
import com.sports.teambuilder.dto.PlayerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<SportsClub>> getAllClubs() {
        log.info("Request list of all the players");
        return new ResponseEntity<>(sportsClubService.getAllPlayersFromDB(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<SportsClub> getClubByName(@PathVariable String name) {
            return new ResponseEntity<>(sportsClubService.findByName(name), HttpStatus.OK);
    }

    @DeleteMapping("delete/name/{name}")
    public void deleteClubByName(@PathVariable String name) {
        sportsClubService.deleteClubByName(name);
    }

    @DeleteMapping("delete/id/{id}")
    public void deleteClubById(@PathVariable String id) {
        sportsClubService.deleteClubById(id);
    }

    @GetMapping("/name/{clubName}/playerList")
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

    @PutMapping("/name/{clubName}")
    public SportsClub updateClubPlayerDetails(@PathVariable String clubName, @RequestBody PlayerDto playerdto) {
        return sportsClubService.addPlayerToClub(clubName, playerdto);
    }

    @PutMapping("/name/{clubName}/{winValue}")
    public SportsClub updateWin(@PathVariable String clubName, @PathVariable Integer winValue) {
        return sportsClubService.updateWinNumber(clubName, winValue);
    }

    @PutMapping("/name/{clubName}/managerDetails")
    public SportsClub updateManagerDetails(@PathVariable String clubName, @RequestBody PlayerDto playerDto) {
        return sportsClubService.updateClubManagerInformation(clubName, playerDto);
    }

    @PutMapping("/name/{clubName}/captainDetails")
    public SportsClub updateCaptainDetails(@PathVariable String clubName, @RequestBody PlayerDto playerDto) {
        return sportsClubService.updateCaptainInformation(clubName, playerDto);
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
