package com.sports.teambuilder.repository.mongo;

import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.SportsClub;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends MongoRepository<SportsClub, String> {

    Optional<SportsClub> findByName(String name);

    @Transactional
    void deleteByName(String name);

    Optional<List<SportsClub>> findBySport(String category);

    default Optional<SportsClub> findClubById(String playerId) {
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