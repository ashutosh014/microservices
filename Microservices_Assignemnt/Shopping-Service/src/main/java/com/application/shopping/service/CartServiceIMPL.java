package com.application.shopping.service;

import com.application.shopping.entity.Cart;
import com.application.shopping.entity.Order;
import com.application.shopping.entity.Product;
import com.application.shopping.external.InvoiceTemplate;
import com.application.shopping.feign.InvoiceInterface;
import com.application.shopping.model.CartTemplate;
import com.application.shopping.repository.CartRepository;
import com.application.shopping.util.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceIMPL implements CartService{

    private final CartRepository cartRepository;
    private final ProductServiceIMPL productService;
    private final OrderServiceIMPL orderService;
    private final EntityManager entityManager;

    public CartServiceIMPL(CartRepository cartRepository,
                           ProductServiceIMPL productService,
                           @Lazy OrderServiceIMPL orderService,
                           EntityManager entityManager) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.orderService = orderService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public String savecart(CartTemplate cartTemplate) {

        List<Product> products = cartTemplate.getProducts();
        if(isProductAvailable(products)) {
            Double price = getPrice(cartTemplate);

            Cart cart = new Cart();

            // Attach products to the current persistence context
            List<Product> managedProducts = cartTemplate.getProducts().stream()
                    .map(product ->
                            entityManager.contains(product) ?
                                    product : entityManager.merge(product))
                    .collect(Collectors.toList());

            cart.setProducts(managedProducts);
            cart.setUserId(cartTemplate.getUserId());
            cart.setTotalPrice(price);

            Cart savedCart = cartRepository.save(cart);

            Order order = getOrder(savedCart);
            orderService.saveOrder(order);

            /*entityManager.flush();
            System.out.println("Flushed changes to Database");*/

            // Trigger the Invoice service
            //String response = (String) invoiceInterface.saveInvoice(prepareInvoice(savedCart)).getBody();
            return "Cart added successfully";
        }
        else {
            return "Unable to add cart as some of the Product/products are not in sufficient quantity";
        }
    }



    @Override
    @Transactional
    public String updateCart(Long id, Cart cart) {
        Optional<Cart> carts = cartRepository.findById(id);

        Cart updatedCart = new Cart();
        if(carts.isPresent() && isProductAvailable(cart.getProducts())) {
            updatedCart.setCartId(id);
           // updatedCart.setTotalPrice(getPrice(cart));
            updatedCart.setUserId(cart.getUserId());
            updatedCart.setProducts(cart.getProducts());
            Double price = getPrice(convertoCartTemplate(cart));
            updatedCart.setTotalPrice(price);


            Order order = orderService.findByCartId(id);
            orderService.updateOrder(order.getOrderId(),order);

            cartRepository.save(updatedCart);

            return "Cart updated successfully";
        }
        else {
            return "Unable to update the cart";
        }
    }

    @Override
    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public List<Product> getProductDetailsbyCartId(Long cartId) {
        Optional<Cart> cartDetails = cartRepository.findById(cartId);
        if(cartDetails.isPresent()) {
            return cartDetails.get().getProducts();
        }
        return List.of();
    }

    @Override
    @Transactional
    public void deleteCart(Long id) {
        Optional<Cart> carts = cartRepository.findById(id);

        if(carts.isPresent()) {
            Order order = orderService.findByCartId(id);
            orderService.deleteOrder(order.getCartID());
            cartRepository.delete(carts.get());
            System.out.println("Cart deleted successfully");
        }
        else {
            System.out.println("Unable to find cart with cart id : " + id);
        }
    }

    private Double getPrice(CartTemplate cart) {
        List<Product> products = cart.getProducts();

        return products.stream()
                .mapToDouble(product -> product.getQuantity() * product.getPrice())
                .sum();
    }

    // Check if the quantity which has been added to the cart
    // is present or not for the specified product
    private boolean isProductAvailable(List<Product> products) {
        for(Product product : products) {
            Optional<Product> findProduct = productService.getProductById(product.getId());

            if(findProduct.isPresent() && findProduct.get().getQuantity() >= product.getQuantity()) {
                return true;
            }
        }
        return false;
    }

    private static Order getOrder(Cart cart) {
        Order order = new Order();
        order.setUserID(cart.getUserId());
        order.setOrderDate(new Date());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setCartID(cart.getCartId());

        return order;
    }

    private CartTemplate convertoCartTemplate(Cart cart) {
        CartTemplate cartTemplate = new CartTemplate();
        cartTemplate.setProducts(cart.getProducts());
        cartTemplate.setTotalPrice(cart.getTotalPrice());
        cartTemplate.setUserId(cart.getUserId());

        return cartTemplate;
    }

}
