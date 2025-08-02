package com.amit.customer.web.controllers;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amit.customer.domain.Customer;
import com.amit.customer.mapstruct.mappers.CustomerMapper;
import com.amit.customer.repository.CustomerRepository;
import com.amit.customer.service.CustomerService;
import com.amit.customer.web.model.CustomerDto;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired 
	CustomerMapper customerMapper;

	@GetMapping("")
	public ResponseEntity<List<CustomerDto>> getAllCustomers() {
		try {
			
			List<CustomerDto> customersResp = customerService.getAllCustomers();
					
			if (customersResp.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
					
			return new ResponseEntity<>(customersResp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") long id) {
		CustomerDto customerDto = customerService.getCustomerById(id);

		if (customerDto != null) {
			return new ResponseEntity<>(customerDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
		try {
			CustomerDto customerDTO = customerService.createCustomer(customerDto);
			return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") long id, @RequestBody CustomerDto customer) {
		
		CustomerDto customerDto = customerService.updateCustomer(id, customer);
		
		if (customerDto != null) {
			
			return new ResponseEntity<>(customerDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
		try {
			CustomerDto existingCustomer = customerService.getCustomerById(id);
			if (existingCustomer == null) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			customerService.deleteCustomer(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
