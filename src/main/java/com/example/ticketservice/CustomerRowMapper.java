package com.example.ticketservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper<Customer> {

	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		int customerId = rs.getInt(Constants.COL_LABEL_CUSTOMER_ID);
		String customerEmail = rs.getString(Constants.COL_LABEL_CUSTOMER_EMAIL);
		return new Customer(customerId, customerEmail);
	}
}