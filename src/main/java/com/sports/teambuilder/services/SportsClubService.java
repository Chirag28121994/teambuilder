package com.sports.teambuilder.services;

import com.sports.teambuilder.repository.mongo.ClubRepository;
import com.sports.teambuilder.repository.mongo.PlayerRepository;
import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.Player;
import com.sports.teambuilder.models.SportsClub;
import com.sports.teambuilder.predicates.BusinessPredicates;
import com.sports.teambuilder.dto.PlayerDto;
import com.sports.teambuilder.dto.SportsClubDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SportsClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BusinessPredicates businessPredicates;

    public List<SportsClub> getAllPlayersFromDB() {
        log.info("Get List of all the players from db");
        return clubRepository.findAll();
    }

    public SportsClub findByName(String name) {
        Optional<SportsClub> sportsClub = clubRepository.findClubByName(name);
        return sportsClub.orElseGet(SportsClub::new);
    }

    public Optional<List<SportsClub>> findByPrimarySport(String sport) {
        return clubRepository.findAllClubBySport(SportsCategory.valueOf(sport));
    }

    public SportsClub saveClubData(SportsClub sportsClub) {
        Optional<SportsClub> existingClub = clubRepository.findClubByName(sportsClub.getName());
        if (existingClub.isEmpty()) {
            return clubRepository.save(sportsClub);
        }
        log.error("Club with name {} is already present ", sportsClub.getName());
        return existingClub.get();
    }

    public Optional<List<PlayerDto>> getAllPlayersOfGivenClub(String clubName) {
        if (clubName == null || clubName.isEmpty()) {
            log.error("club name can't be null or empty");
            throw new IllegalArgumentException("club name can't be null or empty");
        }
        Optional<SportsClub> club = clubRepository.findClubByName(clubName);
        if (club.isEmpty()) {
            log.error("No club found with the nane: {}", clubName);
            return Optional.empty();
        }
        ArrayList<String> playersMobileNumbers = club.get().getAssociatedPlayersMobileNumbers();
        List<Player> players = playersMobileNumbers.stream().map(number -> playerRepository.findByMobileNumber(number).get()).toList();
        List<PlayerDto> playerDtos = players.stream().map(player -> {
                    PlayerDto playerDto = new PlayerDto();
                    playerDto.setName(player.getName());
                    playerDto.setMobileNumber(player.getMobileNumber());
                    return playerDto;
                })
                .collect(Collectors.toList());

        return Optional.of(playerDtos);
    }

    public SportsClub addPlayerToClub(String clubName, PlayerDto playerDto) {
        if (!businessPredicates.ifPlayerMobileRegistered.test(playerDto.getMobileNumber())) {
            Player player = new Player();
            player.setName(playerDto.getName());
            player.setMobileNumber(playerDto.getMobileNumber());

            SportsClub sportsClub = clubRepository.findClubByName(clubName).get();
            player.setPrimarySport(sportsClub.getSport());

            SportsClubDto sportsClubDto = new SportsClubDto();
            sportsClubDto.setSport(sportsClub.getSport());
            sportsClubDto.setName(sportsClub.getName());
            sportsClubDto.setHomeGround(sportsClub.getHomeGround());
            sportsClubDto.setSport(sportsClub.getSport());
            playerRepository.save(player);
        }
        if (!businessPredicates.ifPlayerMobileRegisteredForClub.test(playerDto.getMobileNumber(), clubName)) {
            SportsClub sportsClub = clubRepository.findClubByName(clubName).get();
            sportsClub.getAssociatedPlayersMobileNumbers().add(playerDto.getMobileNumber());
            return saveClubData(sportsClub);
        } else return clubRepository.findClubByName(clubName).get();
    }

    public void deleteClubByName(String name) {
        clubRepository.deleteByName(name);
    }

    public void deleteClubById(String id) {
        clubRepository.deleteById(id);
    }

    public SportsClub updateWinNumber(String clubName, Integer winValue) {
        SportsClub sportsClub = clubRepository.findClubByName(clubName).get();
        sportsClub.setTotalWins(sportsClub.getTotalWins() + winValue);
        return clubRepository.save(sportsClub);
    }

    public SportsClub updateClubManagerInformation(String clubName, PlayerDto playerDto) {
        SportsClub sportsClub = clubRepository.findClubByName(clubName).get();
        sportsClub.setManager(playerDto.getName());
        sportsClub.setManagerContactNumber(playerDto.getMobileNumber());
        return clubRepository.save(sportsClub);
    }

    public SportsClub updateCaptainInformation(String clubName, PlayerDto playerDto) {
        SportsClub sportsClub = clubRepository.findClubByName(clubName).get();
        sportsClub.setCaptain(playerDto.getName());
        sportsClub.setCaptainContactNumber(playerDto.getMobileNumber());
        return clubRepository.save(sportsClub);
    }
}
