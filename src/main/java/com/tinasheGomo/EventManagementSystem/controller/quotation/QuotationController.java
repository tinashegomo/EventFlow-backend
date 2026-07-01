package com.tinasheGomo.EventManagementSystem.controller.quotation;

import com.tinasheGomo.EventManagementSystem.dto.NextNumberResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.quotation.QuotationRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.quotation.QuotationResponseDTO;
import com.tinasheGomo.EventManagementSystem.security.SecurityUtils;
import com.tinasheGomo.EventManagementSystem.service.audit.AuditService;
import com.tinasheGomo.EventManagementSystem.service.counter.CounterService;
import com.tinasheGomo.EventManagementSystem.service.quotation.QuotationService;
import com.tinasheGomo.EventManagementSystem.enums.AuditAction;
import com.tinasheGomo.EventManagementSystem.enums.AuditEntityType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventflow/organizations/{orgId}/quotations")
@RequiredArgsConstructor
public class QuotationController {

    private final QuotationService quotationService;
    private final CounterService counterService;
    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<QuotationResponseDTO>> getAll(@PathVariable String orgId) {
        return ResponseEntity.ok(quotationService.getByOrg(orgId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuotationResponseDTO> getById(@PathVariable String orgId, @PathVariable String id) {
        return ResponseEntity.ok(quotationService.getById(id));
    }

    @PostMapping
    public ResponseEntity<QuotationResponseDTO> create(
            @PathVariable String orgId,
            @Valid @RequestBody QuotationRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        QuotationResponseDTO result = quotationService.create(orgId, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.CREATED, AuditEntityType.QUOTATION,
                result.getId(), result.getQuotationNumber(), "Created quotation", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuotationResponseDTO> update(
            @PathVariable String orgId,
            @PathVariable String id,
            @Valid @RequestBody QuotationRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        QuotationResponseDTO result = quotationService.update(id, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.UPDATED, AuditEntityType.QUOTATION,
                result.getId(), result.getQuotationNumber(), "Updated quotation", null);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String orgId, @PathVariable String id) {
        var user = SecurityUtils.getCurrentUserEntity();
        quotationService.delete(id);
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.DELETED, AuditEntityType.QUOTATION,
                id, "deleted", "Deleted quotation", null);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/next-number")
    public ResponseEntity<NextNumberResponseDTO> getNextNumber(@PathVariable String orgId) {
        String number = counterService.getNextNumber(orgId, "QUO");
        return ResponseEntity.ok(new NextNumberResponseDTO(number));
    }
}
