package com.tinasheGomo.EventManagementSystem.dto.inventory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class InventoryRequestDTO {
    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String category;

    @NotBlank
    private String unit;

    @NotEmpty @Valid
    private List<VariantRequestDTO> variants;
}
