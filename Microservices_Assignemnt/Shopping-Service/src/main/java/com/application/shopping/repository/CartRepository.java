package com.application.shopping.repository;

import com.application.shopping.entity.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
