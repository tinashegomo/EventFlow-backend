package com.tinasheGomo.EventManagementSystem.controller.eventtype;

import com.tinasheGomo.EventManagementSystem.dto.eventtype.EventTypeRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.eventtype.EventTypeResponseDTO;
import com.tinasheGomo.EventManagementSystem.security.SecurityUtils;
import com.tinasheGomo.EventManagementSystem.service.audit.AuditService;
import com.tinasheGomo.EventManagementSystem.service.eventtype.EventTypeService;
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
@RequestMapping("/api/eventflow/organizations/{orgId}/event-types")
@RequiredArgsConstructor
public class EventTypeController {

    private final EventTypeService eventTypeService;
    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<EventTypeResponseDTO>> getAll(@PathVariable String orgId) {
        return ResponseEntity.ok(eventTypeService.getByOrg(orgId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTypeResponseDTO> getById(@PathVariable String orgId, @PathVariable String id) {
        return ResponseEntity.ok(eventTypeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventTypeResponseDTO> create(
            @PathVariable String orgId,
            @Valid @RequestBody EventTypeRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        EventTypeResponseDTO result = eventTypeService.create(orgId, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.CREATED, AuditEntityType.EVENT_TYPE,
                result.getId(), result.getName(), "Created event type", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventTypeResponseDTO> update(
            @PathVariable String orgId,
            @PathVariable String id,
            @Valid @RequestBody EventTypeRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        EventTypeResponseDTO result = eventTypeService.update(id, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.UPDATED, AuditEntityType.EVENT_TYPE,
                result.getId(), result.getName(), "Updated event type", null);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String orgId, @PathVariable String id) {
        var user = SecurityUtils.getCurrentUserEntity();
        eventTypeService.delete(id);
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.DELETED, AuditEntityType.EVENT_TYPE,
                id, "deleted", "Deleted event type", null);
        return ResponseEntity.noContent().build();
    }
}
