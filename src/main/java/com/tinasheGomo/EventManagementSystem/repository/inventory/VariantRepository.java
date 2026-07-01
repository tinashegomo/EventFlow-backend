package com.tinasheGomo.EventManagementSystem.repository.inventory;

import com.tinasheGomo.EventManagementSystem.entity.inventory.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<VariantEntity, String> {
}
