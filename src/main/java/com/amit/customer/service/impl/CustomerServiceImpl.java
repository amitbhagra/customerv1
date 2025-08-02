package com.amit.customer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amit.customer.domain.Customer;
import com.amit.customer.mapstruct.mappers.CustomerMapper;
import com.amit.customer.repository.CustomerRepository;
import com.amit.customer.service.CustomerService;
import com.amit.customer.web.model.CustomerDto;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired 
	CustomerMapper customerMapper;
	
	@Override
	public List<CustomerDto> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDto> customersResp = customerMapper.customerListToCustomerDtoList(customers);
		return customersResp;
	}

	@Override
	public CustomerDto getCustomerById(long id) {
		Optional<Customer> CustomerData = customerRepository.findById(id);
		return CustomerData.isPresent() ? customerMapper.customerToCustomerDto(CustomerData.get()) : null;
	}
	
	@Override
	public CustomerDto getCustomerByEmail(String email) {
		Optional<Customer> CustomerData = customerRepository.findByEmail(email);
		return CustomerData.isPresent() ? customerMapper.customerToCustomerDto(CustomerData.get()) : null;
	}

	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		Customer _customer = customerRepository.save(customerMapper.customerDtoToCustomer(customerDto));
		return customerMapper.customerToCustomerDto(_customer);
	}

	@Override
	@Transactional
	public CustomerDto updateCustomer(long id, CustomerDto customerDto) {
		CustomerDto resp = null;
		Optional<Customer> CustomerData = customerRepository.findById(id);

		if (CustomerData.isPresent()) {
			Customer _Customer = CustomerData.get();
			_Customer.setName(customerDto.getCustomerName());
			_Customer.setEmail(customerDto.getEmail());
			_Customer.setMobile(customerDto.getMobile());
			resp = customerMapper.customerToCustomerDto(_Customer);
		} 
		return resp;
	}

	@Override
	public void deleteCustomer(long id) {
		customerRepository.deleteById(id);
	}

	
	
}
