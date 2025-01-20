package com.application.shopping.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(nullable = false)
    private Long userId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable( name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;


    @Column(nullable = false)
    private Double totalPrice;

    public Cart() {
    }

    public Cart(Long cartId, Long userId, List<Product> products, Double totalPrice) {
        this.cartId = cartId;
        this.userId = userId;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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


    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
