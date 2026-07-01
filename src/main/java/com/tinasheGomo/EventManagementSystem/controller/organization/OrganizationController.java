package com.tinasheGomo.EventManagementSystem.controller.organization;

import com.tinasheGomo.EventManagementSystem.dto.organization.OrganizationRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.organization.OrganizationResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.user.UserEntity;
import com.tinasheGomo.EventManagementSystem.security.SecurityUtils;
import com.tinasheGomo.EventManagementSystem.service.organization.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventflow/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/{orgId}")
    public ResponseEntity<OrganizationResponseDTO> getById(@PathVariable String orgId) {
        return ResponseEntity.ok(organizationService.getById(orgId));
    }

    @PutMapping("/{orgId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationResponseDTO> update(
            @PathVariable String orgId,
            @Valid @RequestBody OrganizationRequestDTO request) {
        return ResponseEntity.ok(organizationService.update(orgId, request));
    }
}
