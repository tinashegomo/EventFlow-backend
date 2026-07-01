package com.tinasheGomo.EventManagementSystem.dto.eventtype;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class PricingTierResponseDTO {
    private String id;
    private Integer guestCount;
    private BigDecimal price;
    private String description;
    private LocalDateTime createdAt;
}
