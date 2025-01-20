package com.application.billing.controller;

import com.application.billing.entity.Invoice;
import com.application.billing.model.InvoiceTemplate;
import com.application.billing.service.InvoiceServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceServiceIMPL invoiceService;

    public InvoiceController(InvoiceServiceIMPL invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveInvoice(@RequestBody InvoiceTemplate invoice) {
        try {
            Invoice createdInvoice = invoiceService.createInvoice(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body("Invoice Created");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create invoice");
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getInvoiceByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok().body(invoiceService.getByOrderId(id));
    }

    @GetMapping("/{id}")
    public Optional<InvoiceTemplate> getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        try {
            Invoice createdInvoice = invoiceService.updateInvoice(id,invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body("Invoice Updated");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update invoice");
        }
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<?> updateInvoiceStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            String result = invoiceService.updateInvoiceStatus(id,status);
            return ResponseEntity.status(HttpStatus.CREATED).body("Invoice Status Updated");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update invoice status");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }

}
