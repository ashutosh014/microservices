package com.application.shopping.service;

import com.application.shopping.entity.Product;
import com.application.shopping.exceptions.ProductNotFoundException;
import com.application.shopping.model.ProductTemplate;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product saveProduct(ProductTemplate createProduct) throws IllegalArgumentException;

    public List<Product> saveAllProducts(List<ProductTemplate> createProducts) throws IllegalArgumentException;

    public Product updateProduct(Long id, ProductTemplate productTemplate) throws ProductNotFoundException;

    public List<ProductTemplate> getAllProducts();

    public Optional<Product> getProductById(Long id);

    public void deleteProduct(Long productId) throws ProductNotFoundException;

    public void updateProductQuantity(Long id, double quantity);

}
