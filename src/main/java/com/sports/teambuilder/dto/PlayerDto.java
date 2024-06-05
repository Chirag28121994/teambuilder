package com.sports.teambuilder.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlayerDto extends ResponseDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String mobileNumber;
}
