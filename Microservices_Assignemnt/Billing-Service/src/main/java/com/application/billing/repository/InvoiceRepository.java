package com.application.billing.repository;

import com.application.billing.entity.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    public Invoice findInvoiceByOrderId(Long orderId);
}
