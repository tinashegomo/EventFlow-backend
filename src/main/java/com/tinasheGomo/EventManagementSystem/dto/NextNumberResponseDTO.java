package com.tinasheGomo.EventManagementSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NextNumberResponseDTO {
    private String number;

    public NextNumberResponseDTO(String number) {
        this.number = number;
    }
}
