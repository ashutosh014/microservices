package com.application.payment.controller;

import com.application.payment.entity.Payment;
import com.application.payment.service.PaymentServiceIMPL;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentContoller {

    private final PaymentServiceIMPL paymentService;

    public PaymentContoller(PaymentServiceIMPL paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody Payment payment) {
        try {
            paymentService.createPayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment Initiated");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to add payment");
        }
    }

    @PutMapping("/status/{id}")
    @CircuitBreaker(name = "invoiceNotFound", fallbackMethod = "invoiceNotFound")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable Long id, @RequestParam String status) {
        String result = paymentService.updatePaymentStatus(id,status);
        return ResponseEntity.ok().body(result);
    }

    // Fallback for INVOICE-SERVICE
    public ResponseEntity<?> invoiceNotFound(@PathVariable Long id,
                                             @RequestParam String status,
                                             Exception exception) {
        return ResponseEntity.badRequest().body("Unable to fetch Invoice details now. Try again later");
    }
}
