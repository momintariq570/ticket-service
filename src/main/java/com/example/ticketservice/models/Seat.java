package com.example.ticketservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "seat")
@NamedQueries(
	{
		@NamedQuery(name = "Seat.GetAllSeats", query = "FROM Seat e ORDER BY e.seatRow, e.seatNumber"),
		@NamedQuery(name = "Seat.GetSeatById", query = "FROM Seat e WHERE e.seatId = :seatId"),
		@NamedQuery(name = "Seat.GetAllSeatsByCustomerEmail", query = "FROM Seat e WHERE e.customerEmail = :customerEmail"),
		@NamedQuery(name = "Seat.GetAllSeatsByCustomerEmailByStatus", query = "FROM Seat e WHERE e.customerEmail = :customerEmail AND e.seatStatus = :seatStatus"),
		@NamedQuery(name = "Seat.GetAllSeatsByStatus", query = "FROM Seat e WHERE e.seatStatus = :seatStatus"),
		@NamedQuery(name = "Seat.UpdateSeatsByCustomerEmail", query = "UPDATE Seat e SET e.seatStatus = :seatStatus, e.customerEmail = :customerEmail WHERE e.seatId in :seatIds"),
		@NamedQuery(name = "Seat.GetSeatsByNumberAndRow", query = "FROM Seat e WHERE e.seatNumber = :seatNumber AND e.seatRow = :seatRow"),
		@NamedQuery(name = "Seat.UpdateSeats", query = "UPDATE Seat e SET e.seatStatus = :seatStatus")
	}
)
@NamedNativeQuery(name = "Seat.InsertSeat", query = "insert into seat (seat_number, seat_row) values (?, ?)")
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int seatId;
	
	@Column(name = "seat_number", nullable = false)
	private int seatNumber;
	
	@Column(name = "seat_row", nullable = false)
	private int seatRow;
	
	@Column(name = "seat_status", columnDefinition = "varchar default 'available'")
	private String seatStatus;
	
	@Column(name = "customer_email")
	private String customerEmail;

	public Seat() {
		super();
	}

	public Seat(int seatNumber, int seatRow) {
		this.seatNumber = seatNumber;
		this.seatRow = seatRow;
	}
	
	public Seat(int seatNumber, int seatRow, String seatStatus, String customerEmail) {
		this.seatNumber = seatNumber;
		this.seatRow = seatRow;
		this.seatStatus = seatStatus;
		this.customerEmail = customerEmail;
	}
	
	public Seat(int seatId, int seatNumber, int seatRow, String seatStatus, String customerEmail) {
		this.seatId = seatId;
		this.seatNumber = seatNumber;
		this.seatRow = seatRow;
		this.seatStatus = seatStatus;
		this.customerEmail = customerEmail;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public String getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(String seatStatus) {
		this.seatStatus = seatStatus;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String toString() {
		return "[seatId: " + this.seatId + " | seatNumber: " + this.seatNumber + " | seatRow: " + this.seatRow
				+ " | seatStatus: " + this.seatStatus + " | customerEmail: " + this.customerEmail + "]";
	}
}
