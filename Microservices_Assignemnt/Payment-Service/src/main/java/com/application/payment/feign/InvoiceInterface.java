package com.application.payment.feign;

import com.application.payment.model.InvoiceTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@FeignClient(name = "BILLING-SERVICE", path = "/invoice")
public interface InvoiceInterface {

    @GetMapping("/{id}")
    public Optional<InvoiceTemplate> getInvoiceById(@PathVariable Long id);

}
