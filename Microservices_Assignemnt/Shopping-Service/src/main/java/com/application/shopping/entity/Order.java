
package com.application.shopping.entity;

import com.application.shopping.util.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_details")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long orderId;

    @Column(nullable = false)
    private Long userID;

    private Long cartID;

    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(Long orderId, Long userID, Long cartID, Date orderDate, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userID = userID;
        this.cartID = cartID;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getCartID() {
        return cartID;
    }

    public void setCartID(Long cartID) {
        this.cartID = cartID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

