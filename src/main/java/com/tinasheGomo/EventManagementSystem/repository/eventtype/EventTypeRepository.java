package com.tinasheGomo.EventManagementSystem.repository.eventtype;

import com.tinasheGomo.EventManagementSystem.entity.eventtype.EventTypeEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventTypeRepository extends JpaRepository<EventTypeEntity, String> {
    List<EventTypeEntity> findByOrganization(OrganizationEntity organization);
}
