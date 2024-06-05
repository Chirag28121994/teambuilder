package com.sports.teambuilder.models;

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
    private String sport;
    private String homeGround;
    private String manager;
    private String captain;
    private String managerContactNumber;
    private String captainContactNumber;
    private Integer totalWins;
    private Integer totalLosses;
    private ArrayList<String> associatedPlayersMobileNumbers;

}
