package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.common.Money;
import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerCreditLimitExceededException;
import com.example.demo.customer.CustomerNotFoundException;
import com.example.demo.customer.CustomerRepository;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class CustomerService {

  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer createCustomer(String name, Money creditLimit) {
    Customer customer  = new Customer(name, creditLimit);
    return customerRepository.save(customer);
  }

  public void reserveCredit(long customerId, long orderId, Money orderTotal) throws CustomerCreditLimitExceededException {
    Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    System.out.println("reserved message");
    customer.reserveCredit(orderId, orderTotal);
  }
}
