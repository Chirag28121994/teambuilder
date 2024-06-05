package com.sports.teambuilder.repository.postgresql;

import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.SportsClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<SportsClub, Integer> {

    Optional<SportsClub> findByName(String name);

    @Transactional
    void deleteByName(String name);

    Optional<List<SportsClub>> findBySport(String category);

    default Optional<SportsClub> findClubById(Integer playerId) {
        if (playerId == null || playerId.describeConstable().isEmpty()) return Optional.empty();
        return findById(playerId);
    }

    default Optional<SportsClub> findClubByName(String name) {
        if (name == null || name.isEmpty()) return Optional.empty();
        return findByName(name);
    }

    default Optional<List<SportsClub>> findAllClubBySport(SportsCategory category) {
        if (category == null) return Optional.empty();
        return findBySport(category.name());
    }

}