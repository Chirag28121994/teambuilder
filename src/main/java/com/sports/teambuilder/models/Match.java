package com.sports.teambuilder.models;

import com.sports.teambuilder.enums.SportsCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "matches")
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private SportsCategory sportType;
    private String teamOne;
    private String teamTwo;
    private String winner;
    private String manOfTheMatch;
    private LocalDate matchDate;
    private String matchDescription;
}
