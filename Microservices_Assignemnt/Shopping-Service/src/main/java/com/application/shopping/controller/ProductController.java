package com.application.shopping.controller;

import com.application.shopping.entity.Product;
import com.application.shopping.exceptions.ProductNotFoundException;
import com.application.shopping.model.ProductTemplate;
import com.application.shopping.service.ProductServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceIMPL productService;

    public ProductController(ProductServiceIMPL productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveProduct(@RequestBody ProductTemplate productTemplate) {
        try {
            productService.saveProduct(productTemplate);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create the product now");
        }
    }

    @PostMapping("/createAll")
    public ResponseEntity<?> saveAllProduct(@RequestBody List<ProductTemplate> productTemplates) {
        try {
            productService.saveAllProducts(productTemplates);
            return ResponseEntity.status(HttpStatus.CREATED).body("Products added successfully");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create the products now");
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductTemplate>> getAllProducts() {
        List<ProductTemplate> products = productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        Optional<Product> product = productService.getProductById(id);

        if(product.isPresent()) {
            return ResponseEntity.ok().body(product);
        }
        else
            throw new ProductNotFoundException("Product is not present for Id {} " + id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductTemplate productTemplate) {
        try {
            Product product = productService.updateProduct(id,productTemplate);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No product found for Id : " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body("Product removed successfully");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No product found for Id : " + id);
        }
    }
}
