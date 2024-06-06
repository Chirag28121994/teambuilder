package com.sports.teambuilder.services;

import com.sports.teambuilder.constants.MatchConstants;
import com.sports.teambuilder.dto.PlayerDto;
import com.sports.teambuilder.dto.PlayerParticipatedDto;
import com.sports.teambuilder.dto.TeamDto;
import com.sports.teambuilder.functions.CommonUtilFunctions;
import com.sports.teambuilder.models.CricketMatch;
import com.sports.teambuilder.repository.mongo.CricketMatchDataRepository;
import com.sports.teambuilder.request.CricketMatchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CricketMatchService {

    @Autowired
    private CricketMatchDataRepository cricketMatchDataRepository;
    @Autowired
    private SportsClubService sportsClubService;

    @Autowired
    private CommonUtilFunctions commonUtilFunctions;


    public CricketMatch scheduleCricketMatch(CricketMatchRequest cricketMatchRequest) {
        CricketMatch cricketMatch = new CricketMatch();
        TeamDto yourClub = new TeamDto();
        yourClub.setClubName(cricketMatchRequest.getYourClubName());
        TeamDto opponentClub = new TeamDto();
        opponentClub.setClubName(cricketMatchRequest.getOpponentClubName());
        cricketMatch.setYourClub(yourClub);
        cricketMatch.setOpponentClub(opponentClub);
        cricketMatch.setMatchScheduleDateTime(LocalDateTime.parse(cricketMatchRequest.getMatchDateTime()));
        cricketMatch.setMatchRequestDateTime(LocalDateTime.now());
        cricketMatch.setMatchCompleted(false);

        Optional<List<PlayerDto>> yourClubPlayerList = Optional.empty();

        try {
            yourClubPlayerList = sportsClubService.getAllPlayersOfGivenClub(cricketMatchRequest.getYourClubName());
        } catch (Exception e) {
            log.error("Failure in fetching your team list");
        }

        // get opposition club's team
        Optional<List<PlayerDto>> oppositionClubPlayerList = Optional.empty();
        try {
            oppositionClubPlayerList = sportsClubService.getAllPlayersOfGivenClub(cricketMatchRequest.getOpponentClubName());
        } catch (Exception e) {
            log.error("Failure in fetching opponent's team list");
        }

        ArrayList<PlayerParticipatedDto> yourPlayerList = new ArrayList<>();
        if (yourClubPlayerList.isPresent())
            yourPlayerList = getPlayersInformationFromListOfPlayerParticipatedDto(yourClubPlayerList);

        ArrayList<PlayerParticipatedDto> opponentPlayerList = new ArrayList<>();
        if (oppositionClubPlayerList.isPresent())
            opponentPlayerList = getPlayersInformationFromListOfPlayerParticipatedDto(oppositionClubPlayerList);

        cricketMatch.getYourClub().setPlayerParticipatedDtoList(yourPlayerList);
        cricketMatch.getOpponentClub().setPlayerParticipatedDtoList(opponentPlayerList);
        String descriptionHeader = createMatchDescription(cricketMatchRequest, MatchConstants.MATCH_SCHEDULE_HEADER_HINDI);
        String descriptionFooter = createMatchDescription(cricketMatchRequest, MatchConstants.MATCH_SCHEDULE_FOOTER_HINDI);
        cricketMatch.setMatchDescriptionHeader(descriptionHeader);
        cricketMatch.setMatchDescriptionFooter(descriptionFooter);

        return cricketMatchDataRepository.save(cricketMatch);
    }

    public ArrayList<PlayerParticipatedDto> getPlayersInformationFromListOfPlayerParticipatedDto(Optional<List<PlayerDto>> clubPlayerList) {
        ArrayList<PlayerParticipatedDto> playerList = new ArrayList<>();

        for (PlayerDto playerDto : clubPlayerList.get()) {
            PlayerParticipatedDto yourPlayer = new PlayerParticipatedDto();
            yourPlayer.setName(playerDto.getName());
            yourPlayer.setMobileNumber(playerDto.getMobileNumber());
            playerList.add(yourPlayer);
        }

        return playerList;
    }

    public HashMap<String, String> getReplacementValue(CricketMatchRequest cricketMatchRequest) {
        HashMap<String, String> replacementValue = new HashMap<>();
        replacementValue.put("YOUR_CLUB_NAME", cricketMatchRequest.getYourClubName());
        replacementValue.put("OPPONENT_CLUB_NAME", cricketMatchRequest.getOpponentClubName());
        replacementValue.put("DATE", cricketMatchRequest.getMatchDateTime());
        replacementValue.put("BELOW_TIME", LocalDateTime.parse(cricketMatchRequest.getMatchDateTime()).minusMinutes(30).toString());
        replacementValue.put("ABOVE_TIME", cricketMatchRequest.getMatchDateTime());
        return replacementValue;
    }

    public String createMatchDescription(CricketMatchRequest cricketMatchRequest, String descriptionConstant) {
        HashMap<String, String> replacementValue = getReplacementValue(cricketMatchRequest);
        return CommonUtilFunctions.replaceString(descriptionConstant, replacementValue);

    }

    public void deleteCricketMatch(String matchId) {
        cricketMatchDataRepository.deleteById(matchId);
    }

    public CricketMatch getMatchDetails(String matchId) {
        return cricketMatchDataRepository.findById(matchId).get();
    }

    public List<PlayerParticipatedDto> getYourClubPlayersWithAcceptance(String matchId) {
        CricketMatch match = cricketMatchDataRepository.findById(matchId).get();
        List<PlayerParticipatedDto> allYourClubPLayer = match.getYourClub().getPlayerParticipatedDtoList();
        return allYourClubPLayer.stream().filter(PlayerParticipatedDto::getAvailableForMatch).toList();

    }

    public List<PlayerParticipatedDto> getYourClubPlayersWithoutAcceptance(String matchId) {
        CricketMatch match = cricketMatchDataRepository.findById(matchId).get();
        List<PlayerParticipatedDto> allYourClubPLayer = match.getYourClub().getPlayerParticipatedDtoList();
        return allYourClubPLayer.stream().filter(player -> player.getAvailableForMatch().equals(false)).toList();

    }

    public CricketMatch setPlayerAvailability(String matchId, PlayerParticipatedDto playerParticipatedDto) {
        CricketMatch match = cricketMatchDataRepository.findById(matchId).get();
        List<PlayerParticipatedDto> yourClubPlayers = match.getYourClub().getPlayerParticipatedDtoList();

        Optional<PlayerParticipatedDto> yourPlayer = yourClubPlayers.stream().filter(player -> player.getMobileNumber().equalsIgnoreCase(playerParticipatedDto.getMobileNumber())).findFirst();
        yourPlayer.get().setAvailableForMatch(playerParticipatedDto.getAvailableForMatch());

        //setting presence to by default availability later if captain finds a player has not come, He can select the one who did not come & mark his absence
        yourPlayer.get().setPresentForMatch(playerParticipatedDto.getAvailableForMatch());

        return cricketMatchDataRepository.save(match);
    }

    public CricketMatch setPlayerAbsence(String matchId, String mobileNumber) {
        CricketMatch match = cricketMatchDataRepository.findById(matchId).get();
        List<PlayerParticipatedDto> yourClubPlayers = match.getYourClub().getPlayerParticipatedDtoList();

        Optional<PlayerParticipatedDto> yourPlayer = yourClubPlayers.stream().filter(player -> player.getMobileNumber().equalsIgnoreCase(mobileNumber)).findFirst();

        yourPlayer.get().setPresentForMatch(false);

        return cricketMatchDataRepository.save(match);
    }
}
