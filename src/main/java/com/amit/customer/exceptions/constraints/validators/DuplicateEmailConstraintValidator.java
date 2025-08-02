package com.amit.customer.exceptions.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.amit.customer.exceptions.constraints.DuplicateEmailConstraint;
import com.amit.customer.service.CustomerService;

public class DuplicateEmailConstraintValidator implements ConstraintValidator<DuplicateEmailConstraint,String>
{

	@Autowired
	private CustomerService customerService;
	
	@Override
	public void initialize(DuplicateEmailConstraint contactNumber) {
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext cxt) {
		return customerService.getCustomerByEmail(email) == null;
	}
}
