package com.application.shopping.service;

import com.application.shopping.entity.Product;
import com.application.shopping.exceptions.ProductNotFoundException;
import com.application.shopping.model.ProductTemplate;
import com.application.shopping.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceIMPL implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceIMPL(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(ProductTemplate createProduct) throws IllegalArgumentException {
        Product product = convertToProduct(createProduct);
        return productRepository.save(product);
    }


    @Override
    public List<Product> saveAllProducts(List<ProductTemplate> createProducts) throws IllegalArgumentException {
        List<Product> productList = createProducts.stream()
                .map(this::convertToProduct)
                .collect(Collectors.toList());

        return (List<Product>) productRepository.saveAll(productList);

    }

    @Override
    public Product updateProduct(Long id, ProductTemplate productTemplate) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        Product updatedProduct = new Product();

        if(product.isPresent()) {
            updatedProduct.setId(id);
            updatedProduct.setProductName(productTemplate.getProductName());
            updatedProduct.setProductDescription(productTemplate.getProductDescription());
            updatedProduct.setPrice(productTemplate.getPrice());
            updatedProduct.setQuantity(productTemplate.getQuantity());

            productRepository.save(updatedProduct);

            return updatedProduct;
        }
        else {
            throw new ProductNotFoundException("Product is not present for Id : " + id);
        }


    }

    @Override
    public List<ProductTemplate> getAllProducts() {
        List<Product> productList = (List<Product>) productRepository.findAll();

        return productList.stream()
                .map(product -> {
                            ProductTemplate productTemplate = new ProductTemplate();
                            productTemplate.setProductName(product.getProductName());
                            productTemplate.setProductDescription(product.getProductDescription());
                            productTemplate.setPrice(product.getPrice());
                            productTemplate.setQuantity(product.getQuantity());
                            return productTemplate;
                        }).toList();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if(product.isPresent()) {
            productRepository.delete(product.get());
            System.out.println("Product removed successfully");
        }
        else
            System.out.println("Unable to find the product with id : " + productId);
            throw new ProductNotFoundException("Product is not present for Id : " + productId);

    }

    private Product convertToProduct(ProductTemplate createProduct) throws IllegalArgumentException {

        Product product = new Product();
        product.setQuantity(createProduct.getQuantity());
        product.setProductDescription(createProduct.getProductDescription());
        product.setProductName(createProduct.getProductName());
        product.setPrice(createProduct.getPrice());

        return product;
    }

    public void updateProductQuantity(Long id, double quantity) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            Product updatedProduct = product.get();
            updatedProduct.setQuantity(quantity);
            productRepository.save(updatedProduct);
        }
    }
}
