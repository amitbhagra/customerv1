package com.amit.customer.web.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.amit.customer.exceptions.constraints.DuplicateEmailConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {
	private Long id;
	@NotBlank(message = "Customer Name must not be blank")
	private String customerName;
	@NotBlank(message = "email must not be blank")
	@Email(message = "email should be well formed")
	@DuplicateEmailConstraint
	private String email;
	@Pattern(regexp="[\\d]{10}", message="Mobile number should be a 10 digit number")
	private String mobile;
}
