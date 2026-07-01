package com.tinasheGomo.EventManagementSystem.dto.eventtype;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class EventTypeRequestDTO {
    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String icon;

    @NotBlank
    private String color;

    @NotEmpty @Valid
    private List<PricingTierRequestDTO> pricingTiers;
}
