package com.sports.teambuilder.models;

import com.sports.teambuilder.enums.SportsCategory;
import dto.PlayerDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity(name = "clubs")
@Data
public class SportsClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String contactNumber;
    private SportsCategory sport;
    private String homeGround;
    private Integer totalWins;
    private Integer totalLosses;
    private ArrayList<PlayerDto> associatedPlayers;

}
