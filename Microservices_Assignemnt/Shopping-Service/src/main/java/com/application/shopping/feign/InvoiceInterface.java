package com.application.shopping.feign;

import com.application.shopping.external.InvoiceTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Billing-Service", path = "/invoice")
public interface InvoiceInterface {

    @PostMapping("/create")
    public ResponseEntity<String> saveInvoice(@RequestBody InvoiceTemplate invoice);
}
