package com.tinasheGomo.EventManagementSystem.dto.quotation;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class QuotationResponseDTO {
    private String id;
    private String quotationNumber;
    private String eventId;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private BigDecimal subtotal;
    private BigDecimal discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal taxPercent;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String status;
    private String validUntil;
    private String notes;
    private String convertedToInvoiceId;
    private String createdBy;
    private String updatedBy;
    private String organizationId;
    private List<LineItemResponseDTO> lineItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
