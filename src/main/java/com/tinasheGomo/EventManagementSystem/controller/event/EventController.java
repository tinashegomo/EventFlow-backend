package com.tinasheGomo.EventManagementSystem.controller.event;

import com.tinasheGomo.EventManagementSystem.dto.event.*;
import com.tinasheGomo.EventManagementSystem.security.SecurityUtils;
import com.tinasheGomo.EventManagementSystem.service.audit.AuditService;
import com.tinasheGomo.EventManagementSystem.service.event.EventService;
import com.tinasheGomo.EventManagementSystem.enums.AuditAction;
import com.tinasheGomo.EventManagementSystem.enums.AuditEntityType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventflow/organizations/{orgId}/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAll(@PathVariable String orgId) {
        return ResponseEntity.ok(eventService.getByOrg(orgId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getById(@PathVariable String orgId, @PathVariable String id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> create(
            @PathVariable String orgId,
            @Valid @RequestBody EventRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        EventResponseDTO result = eventService.create(orgId, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.CREATED, AuditEntityType.EVENT,
                result.getId(), result.getTitle(), "Created event", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> update(
            @PathVariable String orgId,
            @PathVariable String id,
            @Valid @RequestBody EventRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        EventResponseDTO result = eventService.update(id, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.UPDATED, AuditEntityType.EVENT,
                result.getId(), result.getTitle(), "Updated event", null);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String orgId, @PathVariable String id) {
        var user = SecurityUtils.getCurrentUserEntity();
        eventService.delete(id);
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.DELETED, AuditEntityType.EVENT,
                id, "deleted", "Deleted event", null);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<PaymentResponseDTO> recordPayment(
            @PathVariable String orgId,
            @PathVariable String id,
            @Valid @RequestBody PaymentRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        PaymentResponseDTO result = eventService.recordPayment(id, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.PAYMENT_RECORDED, AuditEntityType.EVENT,
                id, "payment", "Recorded payment: $" + request.getAmount(), null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{id}/payments/{paymentId}")
    public ResponseEntity<Void> removePayment(
            @PathVariable String orgId,
            @PathVariable String id,
            @PathVariable String paymentId) {
        var user = SecurityUtils.getCurrentUserEntity();
        eventService.removePayment(id, paymentId);
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.PAYMENT_REMOVED, AuditEntityType.EVENT,
                id, "payment", "Removed payment", null);
        return ResponseEntity.noContent().build();
    }
}
