package com.tinasheGomo.EventManagementSystem.dto.inventory;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class InventoryResponseDTO {
    private String id;
    private String name;
    private String description;
    private String category;
    private String unit;
    private String createdBy;
    private String updatedBy;
    private String organizationId;
    private List<VariantResponseDTO> variants;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
