package com.sports.teambuilder.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private String clubName;
    private String captainName;
    private String managerName;
    private String captainContactNumber;
    private String managerContactNumber;
    private List<PlayerParticipatedDto> playerParticipatedDtoList;
}
