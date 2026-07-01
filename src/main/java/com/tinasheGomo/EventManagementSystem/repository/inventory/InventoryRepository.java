package com.tinasheGomo.EventManagementSystem.repository.inventory;

import com.tinasheGomo.EventManagementSystem.entity.inventory.InventoryEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, String> {
    List<InventoryEntity> findByOrganization(OrganizationEntity organization);
}
