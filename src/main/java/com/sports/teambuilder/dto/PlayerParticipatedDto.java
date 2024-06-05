package com.sports.teambuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerParticipatedDto {
    private String name;
    private String mobileNumber;
    private Boolean availableForMatch;
    private Boolean presentForMatch;
}
