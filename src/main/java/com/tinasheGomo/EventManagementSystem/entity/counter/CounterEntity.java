package com.tinasheGomo.EventManagementSystem.entity.counter;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "counters")
@Getter @Setter @NoArgsConstructor
public class CounterEntity {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false)
    private String prefix;

    @Column(nullable = false)
    private String organizationId;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
