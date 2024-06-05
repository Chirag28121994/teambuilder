package com.sports.teambuilder.repository.mongo;

import com.sports.teambuilder.models.CricketMatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CricketMatchDataRepository extends MongoRepository<CricketMatch, String> {

    Optional<List<CricketMatch>> findByYourClub_ClubName(String yourClub);

    Optional<List<CricketMatch>> findByOpponentClub_ClubName(String opponentClub);

    Optional<List<CricketMatch>> findByMatchScheduleDateTime(LocalDateTime matchScheduleDateTime);

    @Query("{'matchScheduleDateTime' : { $gte: ?0, $lt: ?0 + 86400000 }}")
    Optional<List<CricketMatch>> findByMatchScheduleDate(LocalDate matchScheduleDate);

    @Query("{'matchRequestDateTime' : { $gte: ?0, $lt: ?0 + 86400000 }}")
    Optional<List<CricketMatch>> findByMatchRequestedDate(LocalDate matchRequestDateTime);

    @Query("{'matchScheduleDateTime' : { $gte: ?0, $lte: ?1 }}")
    Optional<List<CricketMatch>> findByMatchScheduleDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{'matchScheduleDateTime' : { $gte: ?0 }}")
    Optional<List<CricketMatch>> findByMatchScheduleDateTimeAfter(LocalDateTime startDate);

    @Query("{'matchScheduleDateTime' : { $lte: ?0 }}")
    Optional<List<CricketMatch>> findByMatchScheduleDateTimeBefore(LocalDateTime endDate);

    Optional<List<CricketMatch>> findByManOfTheMatch_MobileNumber(String mobileNumber);

    @Query("{ '$or': [ { 'yourClub.playerParticipatedDtoList.mobileNumber': ?0 }, { 'opponentClub.playerParticipatedDtoList.mobileNumber': ?0 } ] }")
    Optional<List<CricketMatch>> findByYourClub_PlayerParticipatedDtoList_MobileNumberOrOpponentClub_PlayerParticipatedDtoList_MobileNumber(String mobileNumber);

}
