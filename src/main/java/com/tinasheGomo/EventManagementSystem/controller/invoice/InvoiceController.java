package com.tinasheGomo.EventManagementSystem.controller.invoice;

import com.tinasheGomo.EventManagementSystem.dto.NextNumberResponseDTO;
import com.tinasheGomo.EventManagementSystem.dto.invoice.InvoiceRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.invoice.InvoiceResponseDTO;
import com.tinasheGomo.EventManagementSystem.security.SecurityUtils;
import com.tinasheGomo.EventManagementSystem.service.audit.AuditService;
import com.tinasheGomo.EventManagementSystem.service.counter.CounterService;
import com.tinasheGomo.EventManagementSystem.service.invoice.InvoiceService;
import com.tinasheGomo.EventManagementSystem.enums.AuditAction;
import com.tinasheGomo.EventManagementSystem.enums.AuditEntityType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventflow/organizations/{orgId}/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CounterService counterService;
    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAll(@PathVariable String orgId) {
        return ResponseEntity.ok(invoiceService.getByOrg(orgId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getById(@PathVariable String orgId, @PathVariable String id) {
        return ResponseEntity.ok(invoiceService.getById(id));
    }

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> create(
            @PathVariable String orgId,
            @Valid @RequestBody InvoiceRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        InvoiceResponseDTO result = invoiceService.create(orgId, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.CREATED, AuditEntityType.INVOICE,
                result.getId(), result.getInvoiceNumber(), "Created invoice", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> update(
            @PathVariable String orgId,
            @PathVariable String id,
            @Valid @RequestBody InvoiceRequestDTO request) {
        var user = SecurityUtils.getCurrentUserEntity();
        InvoiceResponseDTO result = invoiceService.update(id, request, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.UPDATED, AuditEntityType.INVOICE,
                result.getId(), result.getInvoiceNumber(), "Updated invoice", null);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String orgId, @PathVariable String id) {
        var user = SecurityUtils.getCurrentUserEntity();
        invoiceService.delete(id);
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.DELETED, AuditEntityType.INVOICE,
                id, "deleted", "Deleted invoice", null);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/mark-paid")
    public ResponseEntity<InvoiceResponseDTO> markAsPaid(@PathVariable String orgId, @PathVariable String id) {
        var user = SecurityUtils.getCurrentUserEntity();
        InvoiceResponseDTO result = invoiceService.markAsPaid(id, user.getId());
        auditService.log(orgId, user.getId(), user.getDisplayName(),
                AuditAction.STATUS_CHANGED, AuditEntityType.INVOICE,
                result.getId(), result.getInvoiceNumber(), "Marked as paid", null);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/next-number")
    public ResponseEntity<NextNumberResponseDTO> getNextNumber(@PathVariable String orgId) {
        String number = counterService.getNextNumber(orgId, "INV");
        return ResponseEntity.ok(new NextNumberResponseDTO(number));
    }
}
