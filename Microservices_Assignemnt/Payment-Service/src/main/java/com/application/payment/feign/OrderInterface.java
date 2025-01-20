package com.application.payment.feign;

import com.application.payment.external.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Shopping-Service", path = "/order")
public interface OrderInterface {

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Product>> getProductDetailsByOrderId(@PathVariable Long id);

}
