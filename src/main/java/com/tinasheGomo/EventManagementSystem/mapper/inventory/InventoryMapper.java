package com.tinasheGomo.EventManagementSystem.mapper.inventory;

import com.tinasheGomo.EventManagementSystem.dto.inventory.InventoryResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.inventory.VariantResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.inventory.InventoryEntity;
import com.tinasheGomo.EventManagementSystem.entity.inventory.VariantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(source = "organization.id", target = "organizationId")
    InventoryResponseDTO toResponse(InventoryEntity entity);
    List<InventoryResponseDTO> toResponseList(List<InventoryEntity> entities);
    VariantResponseDTO toVariantResponse(VariantEntity entity);
    List<VariantResponseDTO> toVariantResponseList(List<VariantEntity> entities);
}
