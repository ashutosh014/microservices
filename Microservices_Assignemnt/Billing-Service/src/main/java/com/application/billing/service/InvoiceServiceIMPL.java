package com.application.billing.service;

import com.application.billing.entity.Invoice;
import com.application.billing.external.Payment;
import com.application.billing.feign.PaymentInterface;
import com.application.billing.model.InvoiceTemplate;
import com.application.billing.repository.InvoiceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InvoiceServiceIMPL implements InvoiceService{

    private static final String INVOICE_PENDING = "PENDING";

    private final InvoiceRepository invoiceRepository;
    private final PaymentInterface paymentInterface;

    public InvoiceServiceIMPL(InvoiceRepository invoiceRepository,
                              PaymentInterface paymentInterface) {
        this.invoiceRepository = invoiceRepository;
        this.paymentInterface = paymentInterface;
    }

    @Override
    public Invoice createInvoice(InvoiceTemplate invoiceTemplate) {
        Invoice invoice = new Invoice();

        invoice.setOrderId(invoiceTemplate.getOrderId());
        invoice.setAmount(invoiceTemplate.getAmount());
        invoice.setUserId(invoiceTemplate.getUserId());
        invoice.setInvoiceDate(new Date());
        invoice.setInvoiceStatus(INVOICE_PENDING);

        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<InvoiceTemplate> getInvoiceById(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if(optionalInvoice.isPresent()) {
            InvoiceTemplate invoiceTemplate = new InvoiceTemplate();
            invoiceTemplate.setOrderId(optionalInvoice.get().getOrderId());
            invoiceTemplate.setAmount(optionalInvoice.get().getAmount());
            invoiceTemplate.setUserId(optionalInvoice.get().getUserId());

            return Optional.of(invoiceTemplate);
        }
        return Optional.empty();
    }

    @Override
    public Invoice getByOrderId(Long orderId) {
        return invoiceRepository.findInvoiceByOrderId(orderId);
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        Invoice updatedInvoice = null;
        if(optionalInvoice.isPresent()) {
            updatedInvoice = optionalInvoice.get();
            updatedInvoice.setInvoiceDate(new Date());
            updatedInvoice.setAmount(invoice.getAmount());
            updatedInvoice.setUserId(invoice.getUserId());
            updatedInvoice.setInvoiceStatus(invoice.getInvoiceStatus());
            updatedInvoice.setOrderId(invoice.getOrderId());

            invoiceRepository.save(updatedInvoice);
        }
        return updatedInvoice;
    }

    @Override
    public String updateInvoiceStatus(Long id, String status) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if(optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoice.setInvoiceStatus(status);

            if(status.equalsIgnoreCase("PROCESSING")) {
                invoiceRepository.save(invoice);
                // If proceeding with invoice, initiate Payment
                Payment payment = new Payment();
                payment.setInvoiceId(invoice.getInvoiceId());
                payment.setPaymentStatus("PENDING");
                payment.setPaymentDate(new Date());
                payment.setAmount(invoice.getAmount());

                String response = paymentInterface.createPayment(payment).getBody();
                System.out.println(response);

                return response;
            }

        }
        return "Not able to update Invoice";
    }

    @Override
    public void deleteInvoice(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        optionalInvoice.ifPresent(invoiceRepository::delete);
    }
}
