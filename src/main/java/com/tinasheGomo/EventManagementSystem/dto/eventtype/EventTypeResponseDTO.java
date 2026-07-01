package com.tinasheGomo.EventManagementSystem.dto.eventtype;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class EventTypeResponseDTO {
    private String id;
    private String name;
    private String description;
    private String icon;
    private String color;
    private String createdBy;
    private String updatedBy;
    private String organizationId;
    private List<PricingTierResponseDTO> pricingTiers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
