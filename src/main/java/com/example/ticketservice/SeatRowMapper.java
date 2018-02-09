package com.example.ticketservice;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SeatRowMapper implements RowMapper<Seat> {

	public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
		int seatId = rs.getInt(Constants.COL_LABEL_SEAT_ID);
		int seatNumber = rs.getInt(Constants.COL_LABEL_SEAT_NUMBER);
		int seatRow = rs.getInt(Constants.COL_LABEL_SEAT_ROW);
		String seatStatus = rs.getString(Constants.COL_LABEL_SEAT_STATUS);
		String customerEmail = rs.getString(Constants.COL_LABEL_CUSTOMER_EMAIL);
		return new Seat(seatId, seatNumber, seatRow, seatStatus, customerEmail);
	}
}
