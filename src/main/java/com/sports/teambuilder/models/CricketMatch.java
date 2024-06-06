package com.sports.teambuilder.models;

import com.sports.teambuilder.dto.PlayerParticipatedDto;
import com.sports.teambuilder.dto.TeamDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "CricketMatch")
@Data
public class CricketMatch {
    @Id
    private String _id;
    private String sportType;
    private TeamDto yourClub;
    private TeamDto opponentClub;
    private String winner;
    private PlayerParticipatedDto manOfTheMatch;
    private LocalDateTime matchScheduleDateTime;
    private LocalDateTime matchRequestDateTime;
    private String matchDescriptionHeader;
    private String matchDescriptionFooter;
    private Boolean matchCompleted;

}
