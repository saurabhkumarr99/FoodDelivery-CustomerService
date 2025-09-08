package com.fooddelivery.customerservice.dto;

import com.fooddelivery.customerservice.model.Customer;

public class CustomerDto {

	private int customerId;
	private String customerName;
	private String email;

	public CustomerDto() {
		super();

	}

	public CustomerDto(int customerId, String customerName, String email) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
	}

	public CustomerDto(Customer customer) {
		super();
		this.customerId = customer.getCustomerId();
		this.customerName = customer.getCustomerName();
		this.email = customer.getEmail();
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CustomerDto [customerId=" + customerId + ", customerName=" + customerName + ", email=" + email + "]";
	}

}
