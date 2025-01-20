package com.application.shopping.controller;

import com.application.shopping.entity.Product;
import com.application.shopping.service.OrderServiceIMPL;
import com.application.shopping.util.OrderStatus;
import jakarta.ws.rs.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final String PENDING = String.valueOf(OrderStatus.PENDING);
    private static final String CANCELLED = String.valueOf(OrderStatus.CANCELLED);
    private static final String PROCESSED = String.valueOf(OrderStatus.PROCESSED);


    private final OrderServiceIMPL orderService;

    public OrderController(OrderServiceIMPL orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> proceedwithOrder(@PathVariable Long id, @RequestParam String decision) {
        try {
            orderService.proceedtoProcessOrder(id,decision);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order Updated");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not Updated");
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity <List<Product>> getProductDetailsByOrderId(@PathVariable Long id) {
        List<Product> products = orderService.getCartProductsbyOrderId(id);
        return ResponseEntity.ok().body(products);
    }



}

