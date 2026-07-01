package com.tinasheGomo.EventManagementSystem.service.invoice;

import com.tinasheGomo.EventManagementSystem.dto.invoice.InvoiceLineItemRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.invoice.InvoiceRequestDTO;
import com.tinasheGomo.EventManagementSystem.dto.invoice.InvoiceResponseDTO;
import com.tinasheGomo.EventManagementSystem.entity.event.EventEntity;
import com.tinasheGomo.EventManagementSystem.entity.invoice.InvoiceEntity;
import com.tinasheGomo.EventManagementSystem.entity.invoice.InvoiceLineItemEntity;
import com.tinasheGomo.EventManagementSystem.entity.organization.OrganizationEntity;
import com.tinasheGomo.EventManagementSystem.entity.quotation.QuotationEntity;
import com.tinasheGomo.EventManagementSystem.enums.InvoiceStatus;
import com.tinasheGomo.EventManagementSystem.enums.QuotationStatus;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.BusinessRuleException;
import com.tinasheGomo.EventManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.EventManagementSystem.mapper.invoice.InvoiceMapper;
import com.tinasheGomo.EventManagementSystem.repository.event.EventRepository;
import com.tinasheGomo.EventManagementSystem.repository.invoice.InvoiceRepository;
import com.tinasheGomo.EventManagementSystem.repository.organization.OrganizationRepository;
import com.tinasheGomo.EventManagementSystem.repository.quotation.QuotationRepository;
import com.tinasheGomo.EventManagementSystem.service.counter.CounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrganizationRepository organizationRepository;
    private final QuotationRepository quotationRepository;
    private final EventRepository eventRepository;
    private final InvoiceMapper invoiceMapper;
    private final CounterService counterService;

    public List<InvoiceResponseDTO> getByOrg(String orgId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));
        return invoiceMapper.toResponseList(invoiceRepository.findByOrganizationOrderByCreatedAtDesc(org));
    }

    public InvoiceResponseDTO getById(String id) {
        InvoiceEntity inv = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found: " + id));
        return invoiceMapper.toResponse(inv);
    }

    @Transactional
    public InvoiceResponseDTO create(String orgId, InvoiceRequestDTO request, String userId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));

        InvoiceEntity inv = new InvoiceEntity();
        inv.setInvoiceNumber(counterService.getNextNumber(orgId, "INV"));

        if (request.getQuotationId() != null) {
            QuotationEntity q = quotationRepository.findById(request.getQuotationId())
                    .orElseThrow(() -> new NotFoundException("Quotation not found: " + request.getQuotationId()));
            q.setStatus(QuotationStatus.CONVERTED);
            q.setConvertedToInvoiceId(inv.getId());
            quotationRepository.save(q);
            inv.setQuotation(q);
        }

        if (request.getEventId() != null) {
            EventEntity event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new NotFoundException("Event not found: " + request.getEventId()));
            inv.setEvent(event);
        }

        inv.setClientName(request.getClientName());
        inv.setClientEmail(request.getClientEmail());
        inv.setClientPhone(request.getClientPhone());
        inv.setDueDate(LocalDate.parse(request.getDueDate()));
        inv.setStatus(request.getStatus() != null
                ? InvoiceStatus.valueOf(request.getStatus()) : InvoiceStatus.DRAFT);
        inv.setNotes(request.getNotes());
        inv.setCreatedBy(userId);
        inv.setOrganization(org);

        inv.setDiscountPercent(request.getDiscountPercent() != null ? request.getDiscountPercent() : BigDecimal.ZERO);
        inv.setTaxPercent(request.getTaxPercent() != null ? request.getTaxPercent() : BigDecimal.ZERO);

        List<InvoiceLineItemEntity> lineItems = new ArrayList<>();
        for (InvoiceLineItemRequestDTO liDTO : request.getLineItems()) {
            InvoiceLineItemEntity li = new InvoiceLineItemEntity();
            li.setDescription(liDTO.getDescription());
            li.setQuantity(liDTO.getQuantity());
            li.setUnitPrice(liDTO.getUnitPrice());
            li.setTotal(liDTO.getUnitPrice().multiply(BigDecimal.valueOf(liDTO.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP));
            li.setInvoice(inv);
            lineItems.add(li);
        }
        inv.setLineItems(lineItems);

        computeTotals(inv);
        inv = invoiceRepository.save(inv);

        if (request.getQuotationId() != null) {
            inv.getQuotation().setConvertedToInvoiceId(inv.getId());
            quotationRepository.save(inv.getQuotation());
        }

        return invoiceMapper.toResponse(inv);
    }

    @Transactional
    public InvoiceResponseDTO update(String id, InvoiceRequestDTO request, String userId) {
        InvoiceEntity inv = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found: " + id));

        inv.setClientName(request.getClientName());
        inv.setClientEmail(request.getClientEmail());
        inv.setClientPhone(request.getClientPhone());
        inv.setDueDate(LocalDate.parse(request.getDueDate()));
        if (request.getStatus() != null) {
            inv.setStatus(InvoiceStatus.valueOf(request.getStatus()));
        }
        inv.setNotes(request.getNotes());
        inv.setUpdatedBy(userId);
        inv.setDiscountPercent(request.getDiscountPercent() != null ? request.getDiscountPercent() : BigDecimal.ZERO);
        inv.setTaxPercent(request.getTaxPercent() != null ? request.getTaxPercent() : BigDecimal.ZERO);

        inv.getLineItems().clear();
        List<InvoiceLineItemEntity> lineItems = new ArrayList<>();
        for (InvoiceLineItemRequestDTO liDTO : request.getLineItems()) {
            InvoiceLineItemEntity li = new InvoiceLineItemEntity();
            li.setDescription(liDTO.getDescription());
            li.setQuantity(liDTO.getQuantity());
            li.setUnitPrice(liDTO.getUnitPrice());
            li.setTotal(liDTO.getUnitPrice().multiply(BigDecimal.valueOf(liDTO.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP));
            li.setInvoice(inv);
            lineItems.add(li);
        }
        inv.getLineItems().addAll(lineItems);

        computeTotals(inv);
        inv = invoiceRepository.save(inv);
        return invoiceMapper.toResponse(inv);
    }

    @Transactional
    public void delete(String id) {
        InvoiceEntity inv = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found: " + id));

        if (inv.getQuotation() != null) {
            QuotationEntity q = inv.getQuotation();
            q.setStatus(QuotationStatus.ACCEPTED);
            q.setConvertedToInvoiceId(null);
            quotationRepository.save(q);
        }

        invoiceRepository.delete(inv);
    }

    @Transactional
    public InvoiceResponseDTO markAsPaid(String id, String userId) {
        InvoiceEntity inv = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found: " + id));
        if (inv.getStatus() == InvoiceStatus.PAID) {
            throw new BusinessRuleException("Invoice is already paid");
        }
        inv.setStatus(InvoiceStatus.PAID);
        inv.setPaidAt(LocalDateTime.now());
        inv.setPaidBy(userId);
        inv = invoiceRepository.save(inv);
        return invoiceMapper.toResponse(inv);
    }

    public void checkOverdueInvoices(String orgId) {
        OrganizationEntity org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Organization not found: " + orgId));
        List<InvoiceEntity> overdue = invoiceRepository
                .findByOrganizationAndStatusInAndDueDateBefore(
                        org, List.of(InvoiceStatus.SENT, InvoiceStatus.DRAFT), LocalDate.now());
        for (InvoiceEntity inv : overdue) {
            inv.setStatus(InvoiceStatus.OVERDUE);
        }
        invoiceRepository.saveAll(overdue);
    }

    private void computeTotals(InvoiceEntity inv) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (InvoiceLineItemEntity li : inv.getLineItems()) {
            subtotal = subtotal.add(li.getTotal());
        }
        inv.setSubtotal(subtotal);

        BigDecimal discountAmount = subtotal
                .multiply(inv.getDiscountPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        inv.setDiscountAmount(discountAmount);

        BigDecimal taxableAmount = subtotal.subtract(discountAmount);
        BigDecimal taxAmount = taxableAmount
                .multiply(inv.getTaxPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        inv.setTaxAmount(taxAmount);

        BigDecimal total = taxableAmount.add(taxAmount).setScale(2, RoundingMode.HALF_UP);
        inv.setTotalAmount(total);
    }
}
