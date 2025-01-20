package com.application.shopping.controller;

import com.application.shopping.entity.Cart;
import com.application.shopping.model.CartTemplate;
import com.application.shopping.service.CartServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartServiceIMPL cartService;

    public CartController(CartServiceIMPL cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody CartTemplate cart) {
        String status = cartService.savecart(cart);

        if(status.contains("Product/products")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to add cart as some of the Product/products are not in sufficient quantity");
        }
        else
            return ResponseEntity.status(HttpStatus.CREATED).body("Cart added successfully");
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        String status = cartService.updateCart(id, cart);

        if(status.contains("Unable")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update cart");
        }
        else
            return ResponseEntity.status(HttpStatus.CREATED).body("Cart updated successfully");
    }


    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        Optional<Cart> cartDetails = cartService.getCartById(id);

        if(cartDetails.isPresent()) {
          return ResponseEntity.ok().body(cartDetails.get());
        }
        return ResponseEntity.badRequest().body(Optional.empty());
    }
}
