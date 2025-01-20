package com.application.billing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long invoiceId;

    private Long userId;
    private Long orderId;
    private Double amount;
    private Date invoiceDate;
    private String invoiceStatus;

    public Invoice() {
    }

    public Invoice(Long invoiceId, Long userId, Long orderId, Double amount, Date invoiceDate, String invoiceStatus) {
        this.invoiceId = invoiceId;
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.invoiceDate = invoiceDate;
        this.invoiceStatus = invoiceStatus;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
