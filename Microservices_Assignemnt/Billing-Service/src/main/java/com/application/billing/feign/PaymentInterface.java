package com.application.billing.feign;

import com.application.billing.external.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Payment-Service", path = "/payment")
public interface PaymentInterface {

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody Payment payment);
}
