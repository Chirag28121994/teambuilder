package com.sports.teambuilder.request;

import lombok.Data;

@Data
public class CricketMatchRequest {
    private String yourClubName;
    private String opponentClubName;
    private String matchDateTime;

}
