package com.example.ticketservice.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ticketservice.repositories.CustomerRepository;
import com.example.ticketservice.repositories.SeatRepository;
import com.example.ticketservice.util.Constants;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class EntitiesTest {

	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private SeatRepository seatRepostory;
	
	@Autowired
	private CustomerRepository customerRepostory;
	
	
	@Test
	public void testCustomerEntity() {
		Customer expected1 = new Customer("abcd");
		testEntityManager.persist(expected1);
		testEntityManager.flush();
		
		Customer expected2 = new Customer("efgh");
		testEntityManager.persist(expected2);
		testEntityManager.flush();
		
		Customer actual1 = customerRepostory.getCustomerByEmail("abcd");
		Customer actual2 = customerRepostory.getCustomerByEmail("efgh");
		
		assertThat(actual1.getCustomerEmail().equals(expected1.getCustomerEmail()));
		assertThat(actual2.getCustomerEmail().equals(expected2.getCustomerEmail()));
		assertThat(customerRepostory.count() == 2);
		assertThat(customerRepostory.getAllCustomers().size() == 2);
	}
	
	@Test
	public void testSeatEntity() {
		// data.sql in src/main/resources inserts 9 seats at runtime
		
		int numberOfSeats = seatRepostory.getAllSeats().size();
		
		assertThat(seatRepostory.count() == 9);
		assertThat(numberOfSeats == 9);
		
		Seat expected1 = new Seat(1, 4, Constants.SEAT_AVAILABLE, "abcd@abcd.com");
		testEntityManager.persist(expected1);
		testEntityManager.flush();
		
		Seat expected2 = new Seat(2, 4, Constants.SEAT_HELD, "efgh@efgh.com");
		testEntityManager.persist(expected2);
		testEntityManager.flush();
		
		Seat actual1 = seatRepostory.getSeatsByNumberAndRow(1, 4);
		Seat actual2 = seatRepostory.getSeatsByNumberAndRow(2, 4);
		
		assertThat(
				actual1.getSeatId() == expected1.getSeatId() &&
				actual1.getCustomerEmail().equals(expected1.getCustomerEmail()) &&
				actual1.getSeatNumber() == expected1.getSeatNumber() &&
				actual1.getSeatRow() == expected1.getSeatRow() &&
				actual1.getSeatStatus().equals(expected1.getSeatStatus())
		);
		
		assertThat(
				actual2.getSeatId() == expected2.getSeatId() &&
				actual2.getCustomerEmail().equals(expected2.getCustomerEmail()) &&
				actual2.getSeatNumber() == expected2.getSeatNumber() &&
				actual2.getSeatRow() == expected2.getSeatRow() &&
				actual2.getSeatStatus().equals(expected2.getSeatStatus())
		);
		
		assertThat(seatRepostory.getAllSeats().size() == 11);
	}
}
