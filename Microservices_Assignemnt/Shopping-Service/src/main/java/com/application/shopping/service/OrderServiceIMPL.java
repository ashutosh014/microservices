package com.application.shopping.service;

import com.application.shopping.entity.Cart;
import com.application.shopping.entity.Order;
import com.application.shopping.entity.Product;
import com.application.shopping.external.InvoiceTemplate;
import com.application.shopping.feign.InvoiceInterface;
import com.application.shopping.repository.OrderRepository;
import com.application.shopping.util.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceIMPL implements OrderService{

    private final OrderRepository orderRepository;
    private final EntityManager entityManager;
    private final InvoiceInterface invoiceInterface;
    private final CartServiceIMPL cartService;
    private final ProductServiceIMPL productService;

    public OrderServiceIMPL(OrderRepository orderRepository,
                            EntityManager entityManager,
                            InvoiceInterface invoiceInterface,
                            @Lazy CartServiceIMPL cartService,
                            @Lazy ProductServiceIMPL productService) {
        this.orderRepository = orderRepository;
        this.entityManager = entityManager;
        this.invoiceInterface = invoiceInterface;
        this.cartService = cartService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public Order findByCartId(long cartId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = orderCriteriaQuery.from(Order.class);

        orderCriteriaQuery.select(orderRoot)
                .where(criteriaBuilder.equal(orderRoot.get("cartID"), cartId));

        TypedQuery<Order> query = entityManager.createQuery(orderCriteriaQuery);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public String updateOrder(Long id, Order order) {
        Optional<Order> findOrder = orderRepository.findById(id);
        if(findOrder.isPresent()) {
            Order updatedOrder = findOrder.get();
            updatedOrder.setOrderStatus(findOrder.get().getOrderStatus());
            updatedOrder.setCartID(findOrder.get().getCartID());
            updatedOrder.setUserID(findOrder.get().getUserID());
            updatedOrder.setOrderDate(findOrder.get().getOrderDate());

            orderRepository.save(updatedOrder);

            return "Order updated successfully";
        }
        else {
            return "Unable to update order";
        }
    }

    @Override
    public String updateStatus(Long id, String status) {
        Optional<Order> findOrder = orderRepository.findById(id);

        if(findOrder.isPresent()) {
            Order updatedOrder = findOrder.get();
            updatedOrder.setOrderStatus(findOrder.get().getOrderStatus());
            orderRepository.save(updatedOrder);
            return "Order status updated successfully";
        }
        else {
            return "Unable to update order";
        }
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Optional<Order> findOrder = orderRepository.findById(id);

        if(findOrder.isPresent()) {
            orderRepository.delete(findOrder.get());
            System.out.println("Order status updated successfully");
        }
        else {
            System.out.println("Unable to delete the order");
        }
    }

    public Order saveOrder(Order order) {

        return orderRepository.save(order);
    }

    @Override
    public String proceedtoProcessOrder(Long id, String decision) {
        Optional<Order> orderDetails = orderRepository.findById(id);

        if(orderDetails.isPresent()) {
            // If the order is processed
            // create the invoice for the order
            if(decision.equalsIgnoreCase("PROCESS")) {
                Order order = orderDetails.get();
                InvoiceTemplate invoiceTemplate = prepareInvoice(order);
                String response = invoiceInterface.saveInvoice(invoiceTemplate).getBody();
                order.setOrderStatus(OrderStatus.PENDING);
                orderRepository.save(order);
                return response;
            }
        }
        return "Order Not found";
    }

    @Override
    public List<Product> getCartProductsbyOrderId(Long orderID) {
        Optional<Order> orderDetail = orderRepository.findById(orderID);
        if(orderDetail.isPresent()) {
            List<Product> productDetails =
                    cartService.getProductDetailsbyCartId(orderDetail.get().getCartID());

            // Update the product quantities
            updateProductQuantities(productDetails);
            return !productDetails.isEmpty() ? productDetails : List.of();
        }
        return List.of();
    }

    private void updateProductQuantities(List<Product> productDetails) {
        for(Product product : productDetails) {
            Optional<Product> productQuantity = productService.getProductById(product.getId());
            productQuantity.ifPresent(value -> productService.updateProductQuantity(value.getId(), (value.getQuantity() - product.getQuantity())));
            System.out.println("Updating the Product quantities");
        }
    }


    private InvoiceTemplate prepareInvoice(Order order) {
        int maxRetries = 5;
        int attempt = 0;

        Double cartPrice = getCartPrice(order);

        while(attempt < maxRetries) {
            try {
                InvoiceTemplate invoiceTemplate = new InvoiceTemplate();
                invoiceTemplate.setAmount(cartPrice);
                invoiceTemplate.setUserId(order.getUserID());
                invoiceTemplate.setOrderId(order.getOrderId());

                return invoiceTemplate;

            }
            catch (Exception e) {
                System.out.println("Retry attempt " + (attempt + 1) + " failed " + e.getMessage());
            }

            attempt+=1;
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
            }
        }

        throw new IllegalStateException("Order details not available after maximum retries");
    }

    private Double getCartPrice(Order order) {
        Optional<Cart> cart = cartService.getCartById(order.getCartID());

        if(cart.isPresent()) {
            return cart.get().getTotalPrice();
        }
        else {
            throw new IllegalArgumentException("Cart details not found");
        }
    }
}
