package com.tinasheGomo.EventManagementSystem.repository.quotation;

import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.entity.quotation.QuotationEntity;
import com.tinasheGomo.EventManagementSystem.enums.QuotationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, String> {
    List<QuotationEntity> findByOrganizationOrderByCreatedAtDesc(OrganizationEntity organization);
    List<QuotationEntity> findByOrganizationAndStatusInAndValidUntilBefore(
            OrganizationEntity organization, List<QuotationStatus> statuses, LocalDate date);
}
