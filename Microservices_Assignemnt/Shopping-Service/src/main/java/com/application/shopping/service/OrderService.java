package com.application.shopping.service;

import com.application.shopping.entity.Cart;
import com.application.shopping.entity.Order;
import com.application.shopping.entity.Product;

import java.util.List;

public interface OrderService {

    public Order findByCartId(long cartId);
    public String updateOrder(Long id, Order order);
    public String updateStatus(Long id, String status);
    public void deleteOrder(Long id);
    public Order saveOrder(Order order);
    public String proceedtoProcessOrder(Long id, String decision);

    public List<Product> getCartProductsbyOrderId(Long orderID);

}
