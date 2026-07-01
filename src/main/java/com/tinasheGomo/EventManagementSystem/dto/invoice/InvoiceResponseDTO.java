package com.tinasheGomo.EventManagementSystem.dto.invoice;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class InvoiceResponseDTO {
    private String id;
    private String invoiceNumber;
    private String quotationId;
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
    private String dueDate;
    private LocalDateTime paidAt;
    private String paidBy;
    private String notes;
    private String createdBy;
    private String updatedBy;
    private String organizationId;
    private List<InvoiceLineItemResponseDTO> lineItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
