package com.example.ticketservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ticketservice.models.Seat;

@Transactional
@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

	@Query(name = "Seat.GetAllSeats")
	public List<Seat> getAllSeats();
	
	@Query(name = "Seat.GetSeatById")
	public Seat getSeatById(@Param("seatId") int seatId);
	
	@Query(name = "Seat.GetAllSeatByCustomerEmail")
	public List<Seat> getAllSeatsByCustomerEmail(@Param("customerEmail") String customerEmail);
	
	@Query(name = "Seat.GetAllSeatsByCustomerEmailByStatus")
	public List<Seat> getAllSeatsByCustomerEmailByStatus(@Param("customerEmail") String customerEmail, @Param("seatStatus") String seatStatus);
	
	@Query(name = "Seat.GetSeatsByNumberAndRow")
	public Seat getSeatsByNumberAndRow(@Param("seatNumber") int seatNumber, @Param("seatRow") int seatRow);
	
	@Query(name = "Seat.GetAllSeatsByStatus")
	public List<Seat> getAllSeatsByStatus(@Param("seatStatus") String seatStatus);	
	
	@Modifying
	@Query(name = "Seat.UpdateSeatsByCustomerEmail")
	public int updateSeatsByCustomerEmail(@Param("seatStatus") String seatStatus, @Param("customerEmail") String customerEmail, @Param("seatIds") List<Integer> seatIds);
	
	@Modifying
	@Query(name = "Seat.UpdateSeats")
	public int updateSeats(@Param("seatStatus") String seatStatus);
	
	@Modifying
	@Query(name = "Seat.InsertSeat")
	public int insertSeat(@Param("seat_number") int seatNumber, @Param("seat_row") int seatRow);
}
