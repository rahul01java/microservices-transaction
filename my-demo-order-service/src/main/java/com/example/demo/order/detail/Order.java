package com.example.demo.order.detail;



import javax.persistence.*;

import com.example.demo.orderdetail.OrderDetails;
import com.example.demo.orderdetail.OrderState;
import com.example.demo.orderdetail.RejectionReason;

@Entity
@Table(name="my_orders")
@Access(AccessType.FIELD)
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private OrderState state;

  @Embedded
  private OrderDetails orderDetails;
  private RejectionReason rejectionReason;

  public Order() {
  }

  public Order(OrderDetails orderDetails) {
    this.orderDetails = orderDetails;
    this.state = OrderState.PENDING;
  }

  public static Order createOrder(OrderDetails orderDetails) {
    return new Order(orderDetails);
  }

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void approve() {
    this.state = OrderState.APPROVED;
  }

  public void reject(RejectionReason rejectionReason) {
    this.state = OrderState.REJECTED;
    this.rejectionReason = rejectionReason;
  }

  public OrderState getState() {
    return state;
  }

  public RejectionReason getRejectionReason() {
    return rejectionReason;
  }
}
