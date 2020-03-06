package com.example.demo.create.saga;

import com.example.demo.orderdetail.OrderDetails;
import com.example.demo.orderdetail.RejectionReason;

public class CreateOrderSagaData {

	private OrderDetails orderDetails;
	private Long orderId;
	private RejectionReason rejectionReason;


	public CreateOrderSagaData(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}


	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public CreateOrderSagaData() {
	}

	public Long getOrderId() {
		return orderId;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setRejectionReason(RejectionReason rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public RejectionReason getRejectionReason() {
		return rejectionReason;
	}
}
