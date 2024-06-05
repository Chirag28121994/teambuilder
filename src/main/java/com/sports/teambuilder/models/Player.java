package com.sports.teambuilder.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String mobileNumber;
    private Boolean isActive;
    private String homeGround;
    private String primarySport;

}
