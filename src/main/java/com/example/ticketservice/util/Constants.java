package com.example.ticketservice.util;

public class Constants {

	// Table names
	public final static String SEAT_TABLE_NM = "seat";
	public final static String CUSTOMER_TABLE_NM = "customer";

	// Seat statuses
	public final static String SEAT_AVAILABLE = "available";
	public final static String SEAT_HELD = "held";
	public final static String SEAT_RESERVED = "reserved";

	// Common column in the two tables above
	public final static String COL_LABEL_CUSTOMER_EMAIL = "customer_email";

	// Column labels for seat table
	public final static String COL_LABEL_SEAT_ID = "seat_id";
	public final static String COL_LABEL_SEAT_NUMBER = "seat_number";
	public final static String COL_LABEL_SEAT_ROW = "seat_row";
	public final static String COL_LABEL_SEAT_STATUS = "seat_status";

	// Column labels for customer table
	public final static String COL_LABEL_CUSTOMER_ID = "customer_id";
}
