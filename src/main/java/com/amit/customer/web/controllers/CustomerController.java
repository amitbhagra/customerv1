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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

// COPILOT MODIFICATION START - KAN-4: Added Swagger/OpenAPI imports for API documentation
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
// COPILOT MODIFICATION END - KAN-4

import com.amit.customer.domain.Customer;
import com.amit.customer.mapstruct.mappers.CustomerMapper;
import com.amit.customer.repository.CustomerRepository;
import com.amit.customer.service.CustomerService;
import com.amit.customer.web.model.CustomerDto;

// COPILOT MODIFICATION START - KAN-4: Added Swagger Tag annotation for API documentation
@Tag(name = "Customer Management", description = "APIs for managing customers")
// COPILOT MODIFICATION END - KAN-4
@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired 
	CustomerMapper customerMapper;

	// COPILOT MODIFICATION START - KAN-4: Added Swagger annotations for getAllCustomers endpoint
	@Operation(summary = "Get all customers", description = "Retrieves a list of all customers in the system")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved list of customers",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
		@ApiResponse(responseCode = "204", description = "No customers found"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	// COPILOT MODIFICATION END - KAN-4
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

	// COPILOT MODIFICATION START - KAN-4: Added Swagger annotations for getCustomerById endpoint
	@Operation(summary = "Get customer by ID", description = "Retrieves a specific customer by their unique identifier")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved customer",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
		@ApiResponse(responseCode = "404", description = "Customer not found")
	})
	// COPILOT MODIFICATION END - KAN-4
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getCustomerById(
		@Parameter(description = "Customer ID", required = true) @PathVariable("id") long id) {
		CustomerDto customerDto = customerService.getCustomerById(id);

		if (customerDto != null) {
			return new ResponseEntity<>(customerDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// COPILOT MODIFICATION START - KAN-4: Added Swagger annotations for createCustomer endpoint
	@Operation(summary = "Create a new customer", description = "Creates a new customer with the provided information")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Customer created successfully",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
		@ApiResponse(responseCode = "400", description = "Invalid input data"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	// COPILOT MODIFICATION END - KAN-4
	@PostMapping("")
	public ResponseEntity<CustomerDto> createCustomer(
		@Parameter(description = "Customer data to be created", required = true) @Valid @RequestBody CustomerDto customerDto) {
		try {
			CustomerDto customerDTO = customerService.createCustomer(customerDto);
			return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		return errors;
	}

	// COPILOT MODIFICATION START - KAN-4: Added Swagger annotations for updateCustomer endpoint
	@Operation(summary = "Update customer", description = "Updates an existing customer with new information")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Customer updated successfully",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))),
		@ApiResponse(responseCode = "404", description = "Customer not found"),
		@ApiResponse(responseCode = "400", description = "Invalid input data")
	})
	// COPILOT MODIFICATION END - KAN-4
	@PutMapping("/{id}")
	public ResponseEntity<CustomerDto> updateCustomer(
		@Parameter(description = "Customer ID", required = true) @PathVariable("id") long id,
		@Parameter(description = "Updated customer data", required = true) @RequestBody CustomerDto customer) {

		CustomerDto customerDto = customerService.updateCustomer(id, customer);
		
		if (customerDto != null) {
			
			return new ResponseEntity<>(customerDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// COPILOT MODIFICATION START - KAN-4: Added Swagger annotations for deleteCustomer endpoint
	@Operation(summary = "Delete customer", description = "Deletes a customer from the system by ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
		@ApiResponse(responseCode = "404", description = "Customer not found"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	// COPILOT MODIFICATION END - KAN-4
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteCustomer(
		@Parameter(description = "Customer ID", required = true) @PathVariable("id") long id) {
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