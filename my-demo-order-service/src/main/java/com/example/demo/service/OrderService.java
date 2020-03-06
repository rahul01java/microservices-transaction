package com.example.demo.service;


import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.create.saga.CreateOrderSaga;
import com.example.demo.create.saga.CreateOrderSagaData;
import com.example.demo.order.detail.Order;
import com.example.demo.orderdetail.OrderDetails;
import com.example.demo.orderdetail.OrderRepository;

public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private SagaInstanceFactory sagaInstanceFactory;

  @Autowired
  private CreateOrderSaga createOrderSaga;

  public OrderService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
    this.orderRepository = orderRepository;
    this.sagaInstanceFactory = sagaInstanceFactory;
    this.createOrderSaga = createOrderSaga;
  }

  @Transactional
  public Order createOrder(OrderDetails orderDetails) {
    CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);
    sagaInstanceFactory.create(createOrderSaga, data);
    return orderRepository.findById(data.getOrderId()).get();
  }

}
