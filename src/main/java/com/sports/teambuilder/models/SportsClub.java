package com.sports.teambuilder.models;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "team_data")
@Data
public class SportsClub {
    @Id
    private String id;
    @Indexed(unique = true)
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
