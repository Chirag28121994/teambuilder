package com.sports.teambuilder.dao;

import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.SportsClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<SportsClub, Integer> {

    Optional<List<SportsClub>> findByName(String name);

    Optional<List<SportsClub>> findByPrimarySport(SportsCategory category);

    default Optional<SportsClub> findClubById(Integer playerId) {
        if (playerId == null || playerId.describeConstable().isEmpty()) return Optional.empty();
        return findById(playerId);
    }

    default Optional<List<SportsClub>> findClubByName(String name) {
        if (name == null || name.isEmpty()) return Optional.empty();
        return findByName(name);
    }

    default Optional<List<SportsClub>> findAllClubBySport(SportsCategory category) {
        if (category == null) return Optional.empty();
        return findByPrimarySport(category);
    }

}