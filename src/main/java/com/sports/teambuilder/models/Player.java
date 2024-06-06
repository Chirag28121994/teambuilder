package com.sports.teambuilder.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "player_data")
@Data
public class Player {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String mobileNumber;
    private Boolean isActive;
    private String homeGround;
    private String primarySport;

}
