package com.sports.teambuilder.predicates;

import com.sports.teambuilder.repository.postgresql.ClubRepository;
import com.sports.teambuilder.repository.postgresql.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Component
public class BusinessPredicates {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ClubRepository clubRepository;

    public Predicate<String> ifPlayerMobileRegistered = ifPlayerMobileRegistered = playerMobile -> playerRepository.findByMobileNumber(playerMobile).isPresent();
    public BiPredicate<String, String> ifPlayerMobileRegisteredForClub = ifPlayerMobileRegisteredForClub = (playerMobile, clubName) -> clubRepository.findClubByName(clubName).map(club -> club.getAssociatedPlayersMobileNumbers().stream().anyMatch(mobile -> mobile.equalsIgnoreCase(playerMobile))).orElse(false);
}
