package com.sports.teambuilder.controller;

import com.sports.teambuilder.dto.PlayerParticipatedDto;
import com.sports.teambuilder.models.CricketMatch;
import com.sports.teambuilder.request.CricketMatchRequest;
import com.sports.teambuilder.services.CricketMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("cricketMatch")
public class CricketMatchController {

    @Autowired
    private CricketMatchService cricketMatchService;

    @PostMapping("scheduleMatch")
    public CricketMatch scheduleCricketMatch(@RequestBody CricketMatchRequest cricketMatchRequest) {
        return cricketMatchService.scheduleCricketMatch(cricketMatchRequest);
    }

    @DeleteMapping("cancelMatch/{matchId}")
    public void cancelCricketMatch(@PathVariable String matchId) {
        cricketMatchService.deleteCricketMatch(matchId);
    }

    @GetMapping("getDetails/{matchId}")
    public CricketMatch getMatchDetails(@PathVariable String matchId) {
        return cricketMatchService.getMatchDetails(matchId);
    }

    @GetMapping("availablePlayers/{matchId}")
    public List<PlayerParticipatedDto> getYourClubPlayerWithAcceptance(@PathVariable String matchId) {
        return cricketMatchService.getYourClubPlayersWithAcceptance(matchId);
    }

    @GetMapping("unAvailablePlayers/{matchId}")
    public List<PlayerParticipatedDto> getYourClubPlayerWithoutAcceptance(@PathVariable String matchId) {
        return cricketMatchService.getYourClubPlayersWithoutAcceptance(matchId);
    }

    @PutMapping("playerResponse/{matchId}")
    public CricketMatch updateYourClubPlayerWithoutAcceptance(@PathVariable String matchId, @RequestBody PlayerParticipatedDto playerParticipatedDto) {
        return cricketMatchService.setPlayerAvailability(matchId, playerParticipatedDto);
    }

    @PutMapping("captainResponse/{matchId}/{playerMobileNumber}")
    public CricketMatch updatePlayerDidNotAttendAfterAccepting(@PathVariable String matchId, @PathVariable String playerMobileNumber) {
        return cricketMatchService.setPlayerAbsence(matchId, playerMobileNumber);
    }
}
