package com.tinasheGomo.EventManagementSystem.repository.invoice;

import com.tinasheGomo.EventManagementSystem.entity.invoice.InvoiceEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {
    List<InvoiceEntity> findByOrganizationOrderByCreatedAtDesc(OrganizationEntity organization);
    List<InvoiceEntity> findByOrganizationAndStatusInAndDueDateBefore(
            OrganizationEntity organization, List<InvoiceStatus> statuses, LocalDate date);
}
