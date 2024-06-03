package com.sports.teambuilder.services;

import com.sports.teambuilder.dao.ClubRepository;
import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.Player;
import com.sports.teambuilder.models.SportsClub;
import dto.PlayerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Slf4j
@Service
public class SportsClubService {

    @Autowired
    public ClubRepository clubRepository;

    public List<SportsClub> getAllPlayersFromDB() {
        log.info("Get List of all the players from db");
        return clubRepository.findAll();
    }

    public Optional<List<SportsClub>> findByName(String name) {
        return clubRepository.findClubByName(name);
    }

    public Optional<List<SportsClub>> findByPrimarySport(String sport) {
        return clubRepository.findAllClubBySport(SportsCategory.valueOf(sport));
    }

    public SportsClub saveClubData(SportsClub sportsClub) {
        return clubRepository.save(sportsClub);
    }

    public Optional<List<PlayerDto>> getAllPlayersOfGivenClub(String clubName) {
        if (clubName == null || clubName.isEmpty()) {
            log.error("clubname can't be null or empty");
            throw new IllegalArgumentException("club name can't be null or empty");
        }
        Optional<List<SportsClub>> club = clubRepository.findClubByName(clubName);
        if (club.isEmpty()) {
            log.error("No club found with the nane: {}", clubName);
            return Optional.empty();
        }
        List<PlayerDto> clubPlayers = club.get().get(0).getAssociatedPlayers();
        return Optional.of(clubPlayers);
    }
}
