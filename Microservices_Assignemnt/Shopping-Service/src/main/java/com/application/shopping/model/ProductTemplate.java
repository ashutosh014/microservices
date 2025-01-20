package com.application.shopping.model;

import lombok.*;


public class ProductTemplate {

    private String productName;
    private String productDescription;
    private Double price;
    private Double quantity;

    public ProductTemplate() {
    }

    public ProductTemplate(String productName, String productDescription, Double price, Double quantity) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
