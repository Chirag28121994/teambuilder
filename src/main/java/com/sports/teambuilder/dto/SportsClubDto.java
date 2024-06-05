package com.sports.teambuilder.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class SportsClubDto extends ResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String sport;
    private String homeGround;
    private ArrayList<PlayerDto> associatedPlayers;

}
