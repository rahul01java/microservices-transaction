package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.order.detail.Order;
import com.example.demo.orderdetail.OrderDetails;
import com.example.demo.orderdetail.OrderRepository;
import com.example.demo.service.OrderService;
import com.example.demo.web.api.CreateOrderRequest;
import com.example.demo.web.api.CreateOrderResponse;
import com.example.demo.web.api.GetOrderResponse;

import java.util.Optional;

@RestController
@RequestMapping("/test")
public class OrderController {

  private OrderService orderService;
  private OrderRepository orderRepository;

  @Autowired
  public OrderController(OrderService orderService, OrderRepository orderRepository) {
    this.orderService = orderService;
    this.orderRepository = orderRepository;
  }

  @RequestMapping(value = "/orders", method = RequestMethod.POST)
  public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
    Order order = orderService.createOrder(new OrderDetails(createOrderRequest.getCustomerId(), createOrderRequest.getOrderTotal()));
    return new CreateOrderResponse(order.getId());
  }

  @RequestMapping(value="/orders/{orderId}", method= RequestMethod.GET)
  public ResponseEntity<GetOrderResponse> getOrder(@PathVariable Long orderId) {
    return orderRepository
            .findById(orderId)
            .map(o -> new ResponseEntity<>(new GetOrderResponse(o.getId(), o.getState(), o.getRejectionReason()), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
