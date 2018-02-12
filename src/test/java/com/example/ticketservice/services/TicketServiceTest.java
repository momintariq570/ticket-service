package com.example.ticketservice.services;

import static org.junit.Assert.assertTrue;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.ticketservice.models.Seat;
import com.example.ticketservice.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TicketServiceTest {
	
	@Autowired
	private TicketService ticketService;
	
	@Before
	public void setUp() {
		ticketService.seatRepository.updateSeats(Constants.SEAT_AVAILABLE);
	}
	
	@Test
	public void testNumSeats() {
		int numSeatsAvailable1 = ticketService.numSeatsAvailable();
		int numSeatsAvailable2 = ticketService.getSeatsByStatus(Constants.SEAT_AVAILABLE).size();
		assertTrue(numSeatsAvailable1 == 9);
		assertTrue(numSeatsAvailable2 == 9);
	}
	
	@Test
	public void testHoldSeatsOneCustomer() {
		String email = "abcd@abcd.com";
		ticketService.findAndHoldSeats(3, email);
		List<Seat> seatsHeld = ticketService.getAllSeatsByEmailByStatus(email, Constants.SEAT_HELD);
		List<Seat> seatsReserved = ticketService.getAllSeatsByEmailByStatus(email, Constants.SEAT_RESERVED);
		assertTrue(seatsHeld.size() == 3);
		assertTrue(seatsReserved.size() == 0);
	}
	
	@Test
	public void testHoldSeatsTwoCustomers() {
		String email1 = "abcd@abcd.com";
		String email2 = "efgh@efgh.com";
		ticketService.findAndHoldSeats(3, email1);
		ticketService.findAndHoldSeats(4, email2);
		List<Seat> seatsHeldEmail1 = ticketService.getAllSeatsByEmailByStatus(email1, Constants.SEAT_HELD);
		List<Seat> seatsReservedEmail1 = ticketService.getAllSeatsByEmailByStatus(email1, Constants.SEAT_RESERVED);
		List<Seat> seatsHeldEmail2 = ticketService.getAllSeatsByEmailByStatus(email2, Constants.SEAT_HELD);
		List<Seat> seatsReservedEmail2 = ticketService.getAllSeatsByEmailByStatus(email2, Constants.SEAT_RESERVED);
		assertTrue(seatsHeldEmail1.size() == 3);
		assertTrue(seatsReservedEmail1.size() == 0);
		assertTrue(seatsHeldEmail2.size() == 4);
		assertTrue(seatsReservedEmail2.size() == 0);
	}
	
	@Test
	public void testHoldSeatsTwoCustomersOverCapacity() {
		String email1 = "abcd@abcd.com";
		String email2 = "efgh@efgh.com";
		ticketService.findAndHoldSeats(3, email1);
		ticketService.findAndHoldSeats(9, email2);
		List<Seat> seatsHeldEmail1 = ticketService.getAllSeatsByEmailByStatus(email1, Constants.SEAT_HELD);
		List<Seat> seatsReservedEmail1 = ticketService.getAllSeatsByEmailByStatus(email1, Constants.SEAT_RESERVED);
		List<Seat> seatsHeldEmail2 = ticketService.getAllSeatsByEmailByStatus(email2, Constants.SEAT_HELD);
		List<Seat> seatsReservedEmail2 = ticketService.getAllSeatsByEmailByStatus(email2, Constants.SEAT_RESERVED);
		assertTrue(seatsHeldEmail1.size() == 3);
		assertTrue(seatsReservedEmail1.size() == 0);
		assertTrue(seatsHeldEmail2.size() == 0);
		assertTrue(seatsReservedEmail2.size() == 0);
	}
	
	@Test
	public void testReserveSeats() {
		String email1 = "abcd@abcd.com";
		ticketService.findAndHoldSeats(3, email1);
		ticketService.reserveSeats(0, email1);
		List<Seat> seatsHeldEmail1 = ticketService.getAllSeatsByEmailByStatus(email1, Constants.SEAT_HELD);
		List<Seat> seatsReservedEmail1 = ticketService.getAllSeatsByEmailByStatus(email1, Constants.SEAT_RESERVED);
		assertTrue(seatsHeldEmail1.size() == 0);
		assertTrue(seatsReservedEmail1.size() == 3);
	}
}
