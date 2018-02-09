package com.example.ticketservice;

public class Customer {

	private int customerId;
	private String customerEmail;

	public Customer(String customerEmail) {
		super();
		this.customerEmail = customerEmail;
	}

	public Customer(int customerId, String customerEmail) {
		super();
		this.customerId = customerId;
		this.customerEmail = customerEmail;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
