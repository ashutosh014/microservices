package com.application.shopping.service;

import com.application.shopping.entity.Cart;
import com.application.shopping.entity.Product;
import com.application.shopping.model.CartTemplate;

import java.util.List;
import java.util.Optional;

public interface CartService {

    public String savecart(CartTemplate cartTemplate);

    public String updateCart(Long id, Cart cart);

    public Optional<Cart> getCartById(Long id);

    public List<Product> getProductDetailsbyCartId(Long cartId);

    public void deleteCart(Long id);
}
