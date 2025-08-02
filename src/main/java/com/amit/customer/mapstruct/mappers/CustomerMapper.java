package com.amit.customer.mapstruct.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.amit.customer.domain.Customer;
import com.amit.customer.web.model.CustomerDto;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	
	@Mapping(target="name", source="customerDto.customerName")
	Customer customerDtoToCustomer(CustomerDto customerDto);
	@Mapping(target="customerName", source="customer.name")
	CustomerDto customerToCustomerDto(Customer customer);
	
	@Mapping(target="customerName", source="customer.name")
	List<CustomerDto> customerListToCustomerDtoList(List<Customer> customer);
}
