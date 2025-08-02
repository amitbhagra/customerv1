package com.amit.customer.exceptions.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.amit.customer.exceptions.constraints.validators.DuplicateEmailConstraintValidator;

@Documented
@Constraint(validatedBy = DuplicateEmailConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateEmailConstraint {
	String message() default "Duplicate email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
