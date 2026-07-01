package com.tinasheGomo.EventManagementSystem.service.inventory;

import com.tinasheGomo.EventManagementSystem.dto.inventory.InventoryRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.inventory.InventoryResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.inventory.VariantRequestDTO;
import com.tinasheGomo.EventManagementSystem.entity.inventory.InventoryEntity;
import com.tinasheGomo.EventManagementSystem.entity.inventory.VariantEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.EventManagementSystem.mapper.inventory.InventoryMapper;
import com.tinasheGomo.EventManagementSystem.repository.inventory.InventoryRepository;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final OrganizationRepository organizationRepository;
    private final InventoryMapper inventoryMapper;

    public List<InventoryResponseDTO> getByOrg(String orgId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));
        return inventoryMapper.toResponseList(inventoryRepository.findByOrganization(org));
    }

    public InventoryResponseDTO getById(String id) {
        InventoryEntity item = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory item not found: " + id));
        return inventoryMapper.toResponse(item);
    }

    @Transactional
    public InventoryResponseDTO create(String orgId, InventoryRequestDTO request, String userId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));

        InventoryEntity item = new InventoryEntity();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setCategory(request.getCategory());
        item.setUnit(request.getUnit());
        item.setCreatedBy(userId);
        item.setOrganization(org);

        List<VariantEntity> variants = new ArrayList<>();
        for (VariantRequestDTO varDTO : request.getVariants()) {
            VariantEntity variant = new VariantEntity();
            variant.setSizeName(varDTO.getSizeName());
            variant.setPricePerUnit(varDTO.getPricePerUnit());
            variant.setQuantityInStock(varDTO.getQuantityInStock());
            variant.setInventory(item);
            variants.add(variant);
        }
        item.setVariants(variants);

        item = inventoryRepository.save(item);
        return inventoryMapper.toResponse(item);
    }

    @Transactional
    public InventoryResponseDTO update(String id, InventoryRequestDTO request, String userId) {
        InventoryEntity item = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory item not found: " + id));

        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setCategory(request.getCategory());
        item.setUnit(request.getUnit());
        item.setUpdatedBy(userId);

        item.getVariants().clear();
        List<VariantEntity> variants = new ArrayList<>();
        for (VariantRequestDTO varDTO : request.getVariants()) {
            VariantEntity variant = new VariantEntity();
            variant.setSizeName(varDTO.getSizeName());
            variant.setPricePerUnit(varDTO.getPricePerUnit());
            variant.setQuantityInStock(varDTO.getQuantityInStock());
            variant.setInventory(item);
            variants.add(variant);
        }
        item.getVariants().addAll(variants);

        item = inventoryRepository.save(item);
        return inventoryMapper.toResponse(item);
    }

    @Transactional
    public void delete(String id) {
        InventoryEntity item = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory item not found: " + id));
        inventoryRepository.delete(item);
    }
}
