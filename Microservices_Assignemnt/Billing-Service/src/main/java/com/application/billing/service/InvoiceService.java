package com.application.billing.service;

import com.application.billing.entity.Invoice;
import com.application.billing.model.InvoiceTemplate;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Optional;

public interface InvoiceService {

    public Invoice createInvoice(InvoiceTemplate invoiceTemplate);

    public Optional<InvoiceTemplate> getInvoiceById(Long id);

    public Invoice getByOrderId(Long orderId);

    public Invoice updateInvoice(Long id, Invoice invoice);

    public String updateInvoiceStatus(Long id, String status);

    public void deleteInvoice(Long id);
}
