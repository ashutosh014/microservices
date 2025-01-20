package com.application.payment.service;

import com.application.payment.entity.Payment;
import com.application.payment.external.Product;
import com.application.payment.feign.InvoiceInterface;
import com.application.payment.feign.OrderInterface;
import com.application.payment.model.InvoiceTemplate;
import com.application.payment.repository.PaymentRepository;
import com.application.payment.util.PaymentStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentServiceIMPL implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceInterface invoiceInterface;
    private final OrderInterface orderInterface;

    public PaymentServiceIMPL(PaymentRepository paymentRepository,
                              InvoiceInterface invoiceInterface,
                              OrderInterface orderInterface) {
        this.paymentRepository = paymentRepository;
        this.invoiceInterface = invoiceInterface;
        this.orderInterface = orderInterface;
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public String updatePayment(Long id, Payment payment) {
        Optional<Payment> paymentDetails = paymentRepository.findById(id);

        if(paymentDetails.isPresent()) {
            Payment updatedPayment = paymentDetails.get();
            updatedPayment.setPaymentDate(payment.getPaymentDate());
            updatedPayment.setPaymentStatus(payment.getPaymentStatus());
            updatedPayment.setAmount(payment.getAmount());
            updatedPayment.setInvoiceId(payment.getInvoiceId());

            paymentRepository.save(updatedPayment);

            return "Payment details updated";
        }
        return "Payment details not found for Id : " + id;
    }


    @Override
    @Transactional
    public String updatePaymentStatus(Long id, String paymentStatus) {
        Optional<Payment> paymentDetails = paymentRepository.findById(id);

        if(paymentDetails.isPresent()) {
            Payment updatedPayment = paymentDetails.get();
            // If the payment is successful, then we need to remove the
            // quantity of the products from the total quantity
            if(paymentStatus.equalsIgnoreCase("SUCCESS")) {
                updatedPayment.setPaymentStatus(PaymentStatus.PROCESSED);
                // Get the invoice details using the invoice_id in payment
                Long orderId = getOrderIdFromInvoice(updatedPayment.getInvoiceId());

                // Using the orderId from invoice detail get the cart details
                List<Product> products = orderInterface.getProductDetailsByOrderId(orderId).getBody();

                System.out.println("Updating payment status as Processed");
                paymentRepository.save(updatedPayment);

                return "Payment Successful";

            }

            if(paymentStatus.equalsIgnoreCase("CANCELLED")) {
                // Check if the payment is already processed
                // If yes cannot update an already processed payment
                // If not update the status to CANCELLED
                if(paymentDetails.get().getPaymentStatus() == PaymentStatus.PROCESSED) {
                    return "Payment is already processed. Cannot update to CANCELLED";
                }
                else {
                    updatedPayment.setPaymentStatus(PaymentStatus.CANCELLED);
                    System.out.println("Updating payment status to CANCELLED");
                    paymentRepository.save(updatedPayment);
                    return "Payment Cancelled";
                }
            }
        }
        return "Unable to update payment details";
    }


    private Long getOrderIdFromInvoice(Long invoiceId) {
        InvoiceTemplate invoiceTemplate = invoiceInterface.getInvoiceById(invoiceId).get();
        return invoiceTemplate.getOrderId();
    }
}
