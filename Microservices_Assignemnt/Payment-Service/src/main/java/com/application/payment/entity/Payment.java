package com.application.payment.entity;

import com.application.payment.util.PaymentStatus;
import jakarta.persistence.*;
import reactor.util.annotation.NonNull;

import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @NonNull
    private Long invoiceId;
    @NonNull
    private Double amount;
    @NonNull
    private Date paymentDate;
    @Enumerated(EnumType.STRING)
    @NonNull
    private PaymentStatus paymentStatus;

    public Payment() {
    }

    public Payment(Long paymentId, @NonNull Long invoiceId, @NonNull Double amount, @NonNull Date paymentDate, @NonNull PaymentStatus paymentStatus) {
        this.paymentId = paymentId;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @NonNull
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(@NonNull Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @NonNull
    public Double getAmount() {
        return amount;
    }

    public void setAmount(@NonNull Double amount) {
        this.amount = amount;
    }

    @NonNull
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(@NonNull Date paymentDate) {
        this.paymentDate = paymentDate;
    }



    @NonNull
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(@NonNull PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
