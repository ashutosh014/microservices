package com.application.shopping.repository;

import com.application.shopping.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order ,Long> {

}
