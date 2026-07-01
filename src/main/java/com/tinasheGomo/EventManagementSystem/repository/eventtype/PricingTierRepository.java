package com.tinasheGomo.EventManagementSystem.repository.eventtype;

import com.tinasheGomo.EventManagementSystem.entity.eventtype.PricingTierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingTierRepository extends JpaRepository<PricingTierEntity, String> {
}
