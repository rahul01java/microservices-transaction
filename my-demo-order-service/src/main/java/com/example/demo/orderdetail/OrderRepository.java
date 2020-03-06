package com.example.demo.orderdetail;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.order.detail.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
