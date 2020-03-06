package com.example.demo.create.saga;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.example.demo.common.Money;
import com.example.demo.customer.api.CustomerCreditLimitExceeded;
import com.example.demo.customer.api.CustomerNotFound;
import com.example.demo.customer.api.ReserveCreditCommand;
import com.example.demo.order.detail.Order;
import com.example.demo.orderdetail.OrderRepository;
import com.example.demo.orderdetail.RejectionReason;

public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

	private OrderRepository orderRepository;

	public CreateOrderSaga(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	private SagaDefinition<CreateOrderSagaData> sagaDefinition = step().invokeLocal(this::create)
			.withCompensation(this::reject).step().invokeParticipant(this::reserveCredit)
			.onReply(CustomerNotFound.class, this::handleCustomerNotFound)
			.onReply(CustomerCreditLimitExceeded.class, this::handleCustomerCreditLimitExceeded).step()
			.invokeLocal(this::approve).build();

	private void handleCustomerNotFound(CreateOrderSagaData data, CustomerNotFound reply) {
		data.setRejectionReason(RejectionReason.UNKNOWN_CUSTOMER);
	}

	private void handleCustomerCreditLimitExceeded(CreateOrderSagaData data, CustomerCreditLimitExceeded reply) {
		data.setRejectionReason(RejectionReason.INSUFFICIENT_CREDIT);
	}

	@Override
	public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
		System.out.println("working");
		return this.sagaDefinition;
	}

	private void create(CreateOrderSagaData data) {
		Order order = Order.createOrder(data.getOrderDetails());
		orderRepository.save(order);
		data.setOrderId(order.getId());
	}

	private CommandWithDestination reserveCredit(CreateOrderSagaData data) {
		long orderId = data.getOrderId();
		Long customerId = data.getOrderDetails().getCustomerId();
		Money orderTotal = data.getOrderDetails().getOrderTotal();
		System.out.println("rply");
		return send(new ReserveCreditCommand(customerId, orderId, orderTotal)).to("customer").build();
	}

	private void approve(CreateOrderSagaData data) {
		orderRepository.findById(data.getOrderId()).get().approve();
	}

	public void reject(CreateOrderSagaData data) {
		orderRepository.findById(data.getOrderId()).get().reject(data.getRejectionReason());
	}

}
