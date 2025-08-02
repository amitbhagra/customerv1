package com.amit.customer.service;

import java.util.List;

import com.amit.customer.web.model.CustomerDto;

public interface CustomerService {
	List<CustomerDto> getAllCustomers();
	CustomerDto getCustomerById(long id);
	CustomerDto createCustomer(CustomerDto customerDto);
	CustomerDto updateCustomer(long id, CustomerDto customerDto);
	void deleteCustomer(long id);
	CustomerDto getCustomerByEmail(String email);
}
