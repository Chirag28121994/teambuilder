package com.sports.teambuilder.repository.postgresql;

import com.sports.teambuilder.enums.SportsCategory;
import com.sports.teambuilder.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Optional<List<Player>> findByName(String name);

    @Transactional
    void deleteByName(String name);

    @Transactional
    void deleteByMobileNumber(String mobileNumber);

    Optional<Player> findByMobileNumber(String mobileNumber);

    Optional<List<Player>> findByPrimarySport(String category);

    default Optional<Player> findPlayerById(Integer playerId) {
        if (playerId == null || playerId.describeConstable().isEmpty()) return Optional.empty();
        return findById(playerId);
    }

    default Optional<List<Player>> findPlayersByName(String name) {
        if (name == null || name.isEmpty()) return Optional.empty();
        return findByName(name.toLowerCase());
    }

    default Optional<List<Player>> findAllPlayersBySport(SportsCategory sport) {
        if (sport == null) return Optional.empty();
        return findByPrimarySport(sport.name());
    }

}