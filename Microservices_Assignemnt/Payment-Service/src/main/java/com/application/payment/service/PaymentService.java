package com.application.payment.service;

import com.application.payment.entity.Payment;

public interface PaymentService {

    public Payment createPayment(Payment payment);
    public String updatePayment(Long id, Payment payment);
    public String updatePaymentStatus(Long id, String paymentStatus);
}
