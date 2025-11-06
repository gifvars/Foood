package com.foood.register.order.repository;


import com.foood.register.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {


    //List<Order> findById(String id);
    //boolean existsByEmail(String email);


}
