package com.example.ticketservice;

public class Seat {

	private int seatId;
	private int seatNumber;
	private int seatRow;
	private String seatStatus;
	private String customerEmail;

	public Seat() {
		super();
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
