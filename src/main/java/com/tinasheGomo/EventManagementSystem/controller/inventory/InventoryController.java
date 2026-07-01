package com.tinasheGomo.EventManagementSystem.controller.inventory;

import com.tinasheGomo.EventManagementSystem.dto.inventory.InventoryRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.inventory.InventoryResponseDTO;
import com.tinasheGomo.EventManagementSystem.security.SecurityUtils;
import com.tinasheGomo.EventManagementSystem.service.audit.AuditService;
import com.tinasheGomo.EventManagementSystem.service.inventory.InventoryService;
import com.tinasheGomo.EventManagementSystem.enums.AuditAction;
import com.tinasheGomo.EventManagementSystem.enums.AuditEntityType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventflow/organizations/{orgId}/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAll(@PathVariable String orgId) {
        return ResponseEntity.ok(inventoryService.getByOrg(orgId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getById(@PathVariable String orgId, @PathVariable String id) {
        return ResponseEntity.ok(inventoryService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDTO> create(
            @PathVariable String orgId,
            @Valid @RequestBody InventoryRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        InventoryResponseDTO result = inventoryService.create(orgId, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.CREATED, AuditEntityType.INVENTORY_ITEM,
                result.getId(), result.getName(), "Created inventory item", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDTO> update(
            @PathVariable String orgId,
            @PathVariable String id,
            @Valid @RequestBody InventoryRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        InventoryResponseDTO result = inventoryService.update(id, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.UPDATED, AuditEntityType.INVENTORY_ITEM,
                result.getId(), result.getName(), "Updated inventory item", null);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String orgId, @PathVariable String id) {
        var user = SecurityUtils.getCurrentUserEntity();
        inventoryService.delete(id);
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.DELETED, AuditEntityType.INVENTORY_ITEM,
                id, "deleted", "Deleted inventory item", null);
        return ResponseEntity.noContent().build();
    }
}
