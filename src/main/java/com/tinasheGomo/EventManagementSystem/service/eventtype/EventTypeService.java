package com.tinasheGomo.EventManagementSystem.service.eventtype;

import com.tinasheGomo.EventManagementSystem.dto.eventtype.EventTypeRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.eventtype.EventTypeResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.eventtype.PricingTierRequestDTO;
import com.tinasheGomo.EventManagementSystem.entity.eventtype.EventTypeEntity;
import com.tinasheGomo.EventManagementSystem.entity.eventtype.PricingTierEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.BusinessRuleException;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.EventManagementSystem.mapper.eventtype.EventTypeMapper;
import com.tinasheGomo.EventManagementSystem.repository.eventtype.EventTypeRepository;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventTypeService {

    private final EventTypeRepository eventTypeRepository;
    private final OrganizationRepository organizationRepository;
    private final EventTypeMapper eventTypeMapper;

    public List<EventTypeResponseDTO> getByOrg(String orgId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));
        return eventTypeMapper.toResponseList(eventTypeRepository.findByOrganization(org));
    }

    public EventTypeResponseDTO getById(String id) {
        EventTypeEntity et = eventTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event type not found: " + id));
        return eventTypeMapper.toResponse(et);
    }

    @Transactional
    public EventTypeResponseDTO create(String orgId, EventTypeRequestDTO request, String userId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));

        EventTypeEntity et = new EventTypeEntity();
        et.setName(request.getName());
        et.setDescription(request.getDescription());
        et.setIcon(request.getIcon());
        et.setColor(request.getColor());
        et.setCreatedBy(userId);
        et.setOrganization(org);

        List<PricingTierEntity> tiers = new ArrayList<>();
        for (PricingTierRequestDTO tierDTO : request.getPricingTiers()) {
            PricingTierEntity tier = new PricingTierEntity();
            tier.setGuestCount(tierDTO.getGuestCount());
            tier.setPrice(tierDTO.getPrice());
            tier.setDescription(tierDTO.getDescription());
            tier.setEventType(et);
            tiers.add(tier);
        }
        tiers.sort(Comparator.comparingInt(PricingTierEntity::getGuestCount));
        et.setPricingTiers(tiers);

        et = eventTypeRepository.save(et);
        return eventTypeMapper.toResponse(et);
    }

    @Transactional
    public EventTypeResponseDTO update(String id, EventTypeRequestDTO request, String userId) {
        EventTypeEntity et = eventTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event type not found: " + id));

        et.setName(request.getName());
        et.setDescription(request.getDescription());
        et.setIcon(request.getIcon());
        et.setColor(request.getColor());
        et.setUpdatedBy(userId);

        et.getPricingTiers().clear();
        List<PricingTierEntity> tiers = new ArrayList<>();
        for (PricingTierRequestDTO tierDTO : request.getPricingTiers()) {
            PricingTierEntity tier = new PricingTierEntity();
            tier.setGuestCount(tierDTO.getGuestCount());
            tier.setPrice(tierDTO.getPrice());
            tier.setDescription(tierDTO.getDescription());
            tier.setEventType(et);
            tiers.add(tier);
        }
        tiers.sort(Comparator.comparingInt(PricingTierEntity::getGuestCount));
        et.getPricingTiers().addAll(tiers);

        et = eventTypeRepository.save(et);
        return eventTypeMapper.toResponse(et);
    }

    @Transactional
    public void delete(String id) {
        EventTypeEntity et = eventTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event type not found: " + id));
        eventTypeRepository.delete(et);
    }
}
