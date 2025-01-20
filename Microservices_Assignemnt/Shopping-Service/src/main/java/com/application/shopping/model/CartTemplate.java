package com.application.shopping.model;

import com.application.shopping.entity.Order;
import com.application.shopping.entity.Product;
import jakarta.persistence.*;

import java.util.List;

public class CartTemplate {
    private Long userId;
    private List<Product> products;
    private Order order;
    private Double totalPrice;

    public CartTemplate() {
    }

    public CartTemplate(Long userId, List<Product> products, Order order, Double totalPrice) {
        this.userId = userId;
        this.products = products;
        this.order = order;
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
