package com.sports.teambuilder.models;

import com.sports.teambuilder.enums.SportsCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String mobileNumber;
    private ArrayList<SportsClub> associatedWithClubs;
    private Boolean isActive;
    private String homeGround;
    private SportsCategory primarySport;

}
