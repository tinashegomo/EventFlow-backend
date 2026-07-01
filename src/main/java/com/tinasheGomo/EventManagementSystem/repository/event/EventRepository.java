package com.tinasheGomo.EventManagementSystem.repository.event;

import com.tinasheGomo.EventManagementSystem.entity.event.EventEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, String> {
    List<EventEntity> findByOrganizationOrderByCreatedAtDesc(OrganizationEntity organization);
    long countByOrganizationAndScheduledDate(OrganizationEntity organization, LocalDate date);
    long countByOrganizationAndScheduledDateBetween(OrganizationEntity organization, LocalDate start, LocalDate end);
}
