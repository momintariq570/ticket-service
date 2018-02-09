package com.example.ticketservice;

import java.util.List;

/*
 * Abstraction which wraps the Seat and Customer models
 */
public class SeatHold {

	private String customerEmail;
	private List<Seat> seats;

	public SeatHold(String customerEmail, List<Seat> seats) {
		super();
		this.customerEmail = customerEmail;
		this.seats = seats;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
