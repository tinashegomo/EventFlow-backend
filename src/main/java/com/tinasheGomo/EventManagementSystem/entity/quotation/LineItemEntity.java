package com.tinasheGomo.EventManagementSystem.entity.quotation;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotation_line_items")
@Getter @Setter @NoArgsConstructor
public class LineItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(nullable = false)
    private String inventoryItemId;

    private String variantId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String snapshotName;

    private String snapshotSize;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal snapshotPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id", nullable = false)
    private QuotationEntity quotation;

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
