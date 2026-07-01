package com.tinasheGomo.EventManagementSystem.dto.quotation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class QuotationRequestDTO {
    private String eventId;

    @NotBlank
    private String clientName;

    private String clientEmail;
    private String clientPhone;

    @NotEmpty @Valid
    private List<LineItemRequestDTO> lineItems;

    private BigDecimal discountPercent;
    private BigDecimal taxPercent;

    @NotNull
    private String validUntil;

    private String status;
    private String notes;
}
