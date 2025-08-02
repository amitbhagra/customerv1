package com.amit.customer.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity {

	private String name;
	private String email;
	private String mobile;
	
	
}
