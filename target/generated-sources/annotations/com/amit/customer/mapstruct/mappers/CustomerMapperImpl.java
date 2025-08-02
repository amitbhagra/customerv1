package com.amit.customer.mapstruct.mappers;

import com.amit.customer.domain.Customer;
import com.amit.customer.web.model.CustomerDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-02T16:55:00+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer customerDtoToCustomer(CustomerDto customerDto) {
        if ( customerDto == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setName( customerDto.getCustomerName() );
        customer.setId( customerDto.getId() );
        customer.setEmail( customerDto.getEmail() );
        customer.setMobile( customerDto.getMobile() );

        return customer;
    }

    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setCustomerName( customer.getName() );
        customerDto.setId( customer.getId() );
        customerDto.setEmail( customer.getEmail() );
        customerDto.setMobile( customer.getMobile() );

        return customerDto;
    }

    @Override
    public List<CustomerDto> customerListToCustomerDtoList(List<Customer> customer) {
        if ( customer == null ) {
            return null;
        }

        List<CustomerDto> list = new ArrayList<CustomerDto>( customer.size() );
        for ( Customer customer1 : customer ) {
            list.add( customerToCustomerDto( customer1 ) );
        }

        return list;
    }
}
