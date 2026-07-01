package com.tinasheGomo.EventManagementSystem.dto.eventtype;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class PricingTierRequestDTO {
    @NotNull @Min(1)
    private Integer guestCount;

    @NotNull @Min(0)
    private BigDecimal price;

    private String description;
}
