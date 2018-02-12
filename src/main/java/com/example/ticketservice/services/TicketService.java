package com.example.ticketservice.services;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.ticketservice.util.Constants;
import com.example.ticketservice.dtos.SeatHold;
import com.example.ticketservice.models.Customer;
import com.example.ticketservice.models.Seat;
import com.example.ticketservice.repositories.CustomerRepository;
import com.example.ticketservice.repositories.SeatRepository;

@Service
public class TicketService implements ITicketService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	SeatRepository seatRepository;

	/*
	 * Get the number of seat(s) available
	 * @return number of seat(s) available
	 * @see com.example.ticketservice.ITicketService#numSeatsAvailable()
	 */
	public int numSeatsAvailable() {
		List<Seat> seats = seatRepository.getAllSeatsByStatus(Constants.SEAT_AVAILABLE);
		logger.info("Retrieved all {} seat(s)", Constants.SEAT_AVAILABLE);
		return seats.size();
	}

	/*
	 * Get all seat(s) by status
	 * @param status
	 * @return list of seat(s) by status
	 */
	public List<Seat> getSeatsByStatus(String status) {
		List<Seat> seats = seatRepository.getAllSeatsByStatus(status);
		logger.info("Retrieved all {} {} seat(s)", seats.size(), status);
		return seats;
	}

	/*
	 * Finds and hold seat(s)
	 * @param numSeats
	 * @param customerEmail
	 * @return SeatHold object that contains information about a customer
	 * and the seat(s) on hold
	 * @see com.example.ticketservice.ITicketService#findAndHoldSeats(int, java.lang.String)
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		// retrieve all available seat(s)
		List<Seat> seats = getSeatsByStatus(Constants.SEAT_AVAILABLE);

		// check if the reservation request can be fulfilled
		if (seats.size() < numSeats) {
			if (seats.size() > 0) {
				logger.info("{} requested {} seat(s), but {} seat(s) are available", customerEmail, numSeats, seats.size());
			} else {
				logger.info("No seat(s) are available");
			}
			return null;
		}

		// select the best seat(s) possible
		seats = findSeats(numSeats, seats);

		// mark the seat(s) as held by the customer
		int rowsAffectedSeats = updateSeatStatus(seats, customerEmail, Constants.SEAT_HELD);
		if (rowsAffectedSeats > 0) {
			logger.info("Held {} seat(s) for {}", rowsAffectedSeats, customerEmail);
		} else {
			logger.info("Failed to hold seat(s) for {}", customerEmail);
			return null;
		}

		seats = getAllSeatsByEmailByStatus(customerEmail, Constants.SEAT_HELD);
		return new SeatHold(customerEmail, seats);
	}

	/*
	 * Reserve the seat(s) for the customer
	 * @param seatHoldId
	 * @param customerEmail
	 * @return confirmation number for the seat(s) reservation
	 * @see com.example.ticketservice.ITicketService#reserveSeats(int, java.lang.String)
	 */
	public String reserveSeats(int seatHoldId, String customerEmail) {

		// get all seat(s) by customer that are on hold
		List<Seat> seats = getAllSeatsByEmailByStatus(customerEmail, Constants.SEAT_HELD);

		// return if the customer does not hold any seat(s)
		if (seats.size() == 0) {
			logger.info("Customer does not have any seats on hold");
			return "";
		}

		// mark the seat(s) as reserved by the customer
		int rowsAffectedSeats = updateSeatStatus(seats, customerEmail, Constants.SEAT_RESERVED);
		if (rowsAffectedSeats > 0) {
			logger.info("Reserved {} seats for {}", rowsAffectedSeats, customerEmail);
		} else {
			logger.info("Failed to reserve seats for {}", customerEmail);
			return "";
		}
		
		return customerEmail + Constants.SEAT_RESERVED.hashCode();
	}

	/*
	 * Releases the seat(s) held by the customer to the public
	 * @param customerEmail
	 */
	public void releaseSeats(String customerEmail) {
		
		// get all seat(s) by customer that are on hold
		List<Seat> seats = getAllSeatsByEmailByStatus(customerEmail, Constants.SEAT_HELD);

		// return if the customer does not hold any seat(s)
		if (seats.size() == 0) {
			logger.info("Customer does not have any seats on hold");
			return;
		}

		// mark the seat(s) as available
		int rowsAffectedSeats = updateSeatStatus(seats, customerEmail, Constants.SEAT_AVAILABLE);
		if (rowsAffectedSeats > 0) {
			logger.info("Released {} seats for {}", rowsAffectedSeats, customerEmail);
		} else {
			logger.info("Failed to release seats for {}", customerEmail);
		}
	}

	/*
	 * Gets seat by id
	 * @param id
	 * @return Seat object
	 */
	public Seat getSeatById(int id) {
		Seat seat = seatRepository.getSeatById(id);
		logger.info("Retrieved seat {} from the seat table", id);
		return seat;
	}

	/*
	 * Gets all seats
	 * @return list of seat(s)
	 */
	public List<Seat> getAllSeats() {
		List<Seat> seats = seatRepository.getAllSeats();
		logger.info("Retrieved all {} seats from the seat table", seats.size());
		return seats;
	}

	/*
	 * Gets all seat(s) by customer's email
	 * @param customerEmail
	 * @return list of seat(s)
	 */
	public List<Seat> getAllSeatsByEmailAddress(String customerEmail) {
		return seatRepository.getAllSeatsByCustomerEmail(customerEmail);
	}

	/*
	 * Gets all seat(s) by customer's email and by status
	 * @param customerEmail
	 * @param status 
	 * @return list of seat(s)
	 */
	public List<Seat> getAllSeatsByEmailByStatus(String customerEmail, String status) {
		return seatRepository.getAllSeatsByCustomerEmailByStatus(customerEmail, status);
	}

	/*
	 * Insert new customer in the database
	 * @param customerEmail
	 * @return boolean if the customer was added or not
	 */
	public boolean insertNewCustomer(String customerEmail) {
		
		// Check if the customer is already in the database
		Customer customer = customerRepository.getCustomerByEmail(customerEmail);

		// Return false if the customer is already in the database
		if (customer != null) {
			logger.info("Customer already exists in the system");
			return false;
		}

		// Insert the customer in the database
		int rowsAffected = customerRepository.addNewCustomer(customerEmail);

		if (rowsAffected > 0) {
			logger.info("Inserted {} in the customer table", customerEmail);
			return true;
		} else {
			logger.info("Failed to insert {} in the customer table", customerEmail);
			return false;
		}
	}

	/*
	 * Deletes customer from the table
	 * @param customerEmail
	 */
	public void deleteCustomer(String customerEmail) {
		int rowsAffected = customerRepository.deleteCustomerByEmail(customerEmail);
		if (rowsAffected > 0) {
			logger.info("Deleted {} from the customer table", customerEmail);
		} else {
			logger.info("Failed to delete {} from the customer table", customerEmail);
		}
	}

	/*
	 * Finds the number of seat(s) that are closest to the stage
	 * @param numSeats
	 * @param availableSeats
	 * @return seats found for the customer
	 */
	private List<Seat> findSeats(int numSeats, List<Seat> availableSeats) {
		List<Seat> foundSeats = new ArrayList<Seat>();

		for (int i = 0; i < numSeats; i++) {
			foundSeats.add(availableSeats.get(i));
		}
		return foundSeats;
	}
	
	/*
	 * Updates the seat(s) status
	 * @param seats 
	 * @param customerEmail 
	 * @param status
	 * @return number of rows affected
	 */
	private int updateSeatStatus(List<Seat> seats, String customerEmail, String status) {
		ArrayList<Integer> seatIds = new ArrayList<Integer>();
		seats.forEach(seat -> {
			seatIds.add(seat.getSeatId());
		});
		customerEmail = status.equals(Constants.SEAT_AVAILABLE) ? null : customerEmail;
		int rowsAffectedSeats = seatRepository.updateSeatsByCustomerEmail(status, customerEmail, seatIds);
		return rowsAffectedSeats;
	}
}
