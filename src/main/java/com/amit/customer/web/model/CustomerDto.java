package com.amit.customer.web.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// COPILOT MODIFICATION START - KAN-4: Added Swagger Schema annotation import for API documentation
import io.swagger.v3.oas.annotations.media.Schema;
// COPILOT MODIFICATION END - KAN-4

import com.amit.customer.exceptions.constraints.DuplicateEmailConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// COPILOT MODIFICATION START - KAN-4: Added Swagger Schema annotations for API documentation
@Schema(description = "Customer Data Transfer Object")
// COPILOT MODIFICATION END - KAN-4
@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {
	// COPILOT MODIFICATION START - KAN-4: Added Schema annotation for id field
	@Schema(description = "Unique identifier for the customer", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	// COPILOT MODIFICATION END - KAN-4
	private Long id;

	// COPILOT MODIFICATION START - KAN-4: Added Schema annotation for customerName field
	@Schema(description = "Name of the customer", example = "John Doe", required = true)
	// COPILOT MODIFICATION END - KAN-4
	@NotBlank(message = "Customer Name must not be blank")
	private String customerName;

	// COPILOT MODIFICATION START - KAN-4: Added Schema annotation for email field
	@Schema(description = "Email address of the customer", example = "john.doe@example.com", required = true)
	// COPILOT MODIFICATION END - KAN-4
	@NotBlank(message = "email must not be blank")
	@Email(message = "email should be well formed")
	@DuplicateEmailConstraint
	private String email;

	// COPILOT MODIFICATION START - KAN-4: Added Schema annotation for mobile field
	@Schema(description = "Mobile number of the customer", example = "9876543210", required = true)
	// COPILOT MODIFICATION END - KAN-4
	@Pattern(regexp="[\\d]{10}", message="Mobile number should be a 10 digit number")
	private String mobile;
}
