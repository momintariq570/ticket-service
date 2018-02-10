package com.example.ticketservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query(name = "Customer.GetAllCustomers")
	public List<Customer> getAllCustomers();
	
	@Query(name = "Customer.GetCustomerByEmail")
	public Customer getCustomerByEmail(@Param("customerEmail") String customerEmail);
	
	@Modifying
	@Query(name = "Customer.AddNewCustomer")
	public int addNewCustomer(@Param("customer_email") String customerEmail);
	
	@Modifying
	@Query(name = "Customer.DeleteCustomerByEmail")
	public int deleteCustomerByEmail(@Param("customerEmail") String customerEmail);
}
