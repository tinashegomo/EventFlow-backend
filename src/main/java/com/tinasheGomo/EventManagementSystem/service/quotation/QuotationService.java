package com.tinasheGomo.EventManagementSystem.service.quotation;

import com.tinasheGomo.EventManagementSystem.dto.quotation.LineItemRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.quotation.QuotationRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.quotation.QuotationResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.event.EventEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.entity.quotation.LineItemEntity;
import com.tinasheGomo.EventManagementSystem.entity.quotation.QuotationEntity;
import com.tinasheGomo.EventManagementSystem.enums.QuotationStatus;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.EventManagementSystem.mapper.quotation.QuotationMapper;
import com.tinasheGomo.EventManagementSystem.repository.event.EventRepository;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import com.tinasheGomo.EventManagementSystem.repository.quotation.QuotationRepository;
import com.tinasheGomo.EventManagementSystem.service.counter.CounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final OrganizationRepository organizationRepository;
    private final EventRepository eventRepository;
    private final QuotationMapper quotationMapper;
    private final CounterService counterService;

    public List<QuotationResponseDTO> getByOrg(String orgId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));
        return quotationMapper.toResponseList(quotationRepository.findByOrganizationOrderByCreatedAtDesc(org));
    }

    public QuotationResponseDTO getById(String id) {
        QuotationEntity q = quotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation not found: " + id));
        return quotationMapper.toResponse(q);
    }

    @Transactional
    public QuotationResponseDTO create(String orgId, QuotationRequestDTO request, String userId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));

        QuotationEntity q = new QuotationEntity();
        q.setQuotationNumber(counterService.getNextNumber(orgId, "QUO"));

        if (request.getEventId() != null) {
            EventEntity event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new NotFoundException("Event not found: " + request.getEventId()));
            q.setEvent(event);
        }

        q.setClientName(request.getClientName());
        q.setClientEmail(request.getClientEmail());
        q.setClientPhone(request.getClientPhone());
        q.setValidUntil(LocalDate.parse(request.getValidUntil()));
        q.setStatus(request.getStatus() != null
                ? QuotationStatus.valueOf(request.getStatus()) : QuotationStatus.DRAFT);
        q.setNotes(request.getNotes());
        q.setCreatedBy(userId);
        q.setOrganization(org);

        q.setDiscountPercent(request.getDiscountPercent() != null ? request.getDiscountPercent() : BigDecimal.ZERO);
        q.setTaxPercent(request.getTaxPercent() != null ? request.getTaxPercent() : BigDecimal.ZERO);

        List<LineItemEntity> lineItems = new ArrayList<>();
        for (LineItemRequestDTO liDTO : request.getLineItems()) {
            LineItemEntity li = new LineItemEntity();
            li.setInventoryItemId(liDTO.getInventoryItemId());
            li.setVariantId(liDTO.getVariantId());
            li.setQuantity(liDTO.getQuantity());
            li.setSnapshotName(liDTO.getSnapshotName());
            li.setSnapshotSize(liDTO.getSnapshotSize());
            li.setSnapshotPrice(liDTO.getSnapshotPrice());
            li.setQuotation(q);
            lineItems.add(li);
        }
        q.setLineItems(lineItems);

        computeTotals(q);
        q = quotationRepository.save(q);
        return quotationMapper.toResponse(q);
    }

    @Transactional
    public QuotationResponseDTO update(String id, QuotationRequestDTO request, String userId) {
        QuotationEntity q = quotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation not found: " + id));

        q.setClientName(request.getClientName());
        q.setClientEmail(request.getClientEmail());
        q.setClientPhone(request.getClientPhone());
        q.setValidUntil(LocalDate.parse(request.getValidUntil()));
        if (request.getStatus() != null) {
            q.setStatus(QuotationStatus.valueOf(request.getStatus()));
        }
        q.setNotes(request.getNotes());
        q.setUpdatedBy(userId);
        q.setDiscountPercent(request.getDiscountPercent() != null ? request.getDiscountPercent() : BigDecimal.ZERO);
        q.setTaxPercent(request.getTaxPercent() != null ? request.getTaxPercent() : BigDecimal.ZERO);

        q.getLineItems().clear();
        List<LineItemEntity> lineItems = new ArrayList<>();
        for (LineItemRequestDTO liDTO : request.getLineItems()) {
            LineItemEntity li = new LineItemEntity();
            li.setInventoryItemId(liDTO.getInventoryItemId());
            li.setVariantId(liDTO.getVariantId());
            li.setQuantity(liDTO.getQuantity());
            li.setSnapshotName(liDTO.getSnapshotName());
            li.setSnapshotSize(liDTO.getSnapshotSize());
            li.setSnapshotPrice(liDTO.getSnapshotPrice());
            li.setQuotation(q);
            lineItems.add(li);
        }
        q.getLineItems().addAll(lineItems);

        computeTotals(q);
        q = quotationRepository.save(q);
        return quotationMapper.toResponse(q);
    }

    @Transactional
    public void delete(String id) {
        QuotationEntity q = quotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation not found: " + id));
        quotationRepository.delete(q);
    }

    public void markExpiredQuotations(String orgId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));
        List<QuotationEntity> expired = quotationRepository
                .findByOrganizationAndStatusInAndValidUntilBefore(
                        org, List.of(QuotationStatus.DRAFT, QuotationStatus.SENT), LocalDate.now());
        for (QuotationEntity q : expired) {
            q.setStatus(QuotationStatus.EXPIRED);
        }
        quotationRepository.saveAll(expired);
    }

    private void computeTotals(QuotationEntity q) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (LineItemEntity li : q.getLineItems()) {
            BigDecimal lineTotal = li.getSnapshotPrice()
                    .multiply(BigDecimal.valueOf(li.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);
            subtotal = subtotal.add(lineTotal);
        }
        q.setSubtotal(subtotal);

        BigDecimal discountAmount = subtotal
                .multiply(q.getDiscountPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        q.setDiscountAmount(discountAmount);

        BigDecimal taxableAmount = subtotal.subtract(discountAmount);
        BigDecimal taxAmount = taxableAmount
                .multiply(q.getTaxPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        q.setTaxAmount(taxAmount);

        BigDecimal total = taxableAmount.add(taxAmount).setScale(2, RoundingMode.HALF_UP);
        q.setTotalAmount(total);
    }
}
