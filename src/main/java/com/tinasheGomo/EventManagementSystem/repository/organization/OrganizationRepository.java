package com.tinasheGomo.EventManagementSystem.repository.organization;

import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {
}
