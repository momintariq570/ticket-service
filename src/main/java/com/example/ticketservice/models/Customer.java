package com.example.ticketservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@NamedQueries(
	{
		@NamedQuery(name = "Customer.GetAllCustomers", query = "FROM Customer c"),
		@NamedQuery(name = "Customer.GetCustomerByEmail", query = "FROM Customer c WHERE c.customerEmail = :customerEmail"),
		@NamedQuery(name = "Customer.DeleteCustomerByEmail", query = "DELETE Customer c WHERE c.customerEmail = :customerEmail")
	}
)

@NamedNativeQueries(
	{
		@NamedNativeQuery(name = "Customer.AddNewCustomer", query = "insert into customer(customer_email) values (?)")
	}
)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	
	@Column(name = "customer_email", nullable = false)
	private String customerEmail;

	public Customer() {
		super();
	}
	
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
	
	public String toString() {
		return "[customerId: " + this.customerId + " | customerEmail: " + this.customerEmail +  "]";
	}
}
