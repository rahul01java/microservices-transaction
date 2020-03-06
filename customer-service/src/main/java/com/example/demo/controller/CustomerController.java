package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.customer.Customer;
import com.example.demo.service.CustomerService;

@RestController
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
		Customer customer = customerService.createCustomer(createCustomerRequest.getName(),
				createCustomerRequest.getCreditLimit());
		return new CreateCustomerResponse(customer.getId());
	}
}
