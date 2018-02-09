package com.example.ticketservice;

/*
 * Sqls used by the ticket service
 */
public class Sql {

	public static final String GET_SEAT_BY_ID = "select * from " + Constants.SEAT_TABLE_NM + " where " + Constants.COL_LABEL_SEAT_ID + " = ?;";
	public static final String GET_ALL_SEATS_BY_EMAIL_ADDRESS = "select * from " + Constants.SEAT_TABLE_NM + " where " + Constants.COL_LABEL_CUSTOMER_EMAIL + " = ?;";
	public static final String GET_ALL_SEATS_BY_EMAIL_ADDRESS_BY_STATUS = "select * from " + Constants.SEAT_TABLE_NM + " where " + Constants.COL_LABEL_CUSTOMER_EMAIL + " = ? and " + Constants.COL_LABEL_SEAT_STATUS + " = ?;";
	public static final String GET_ALL_SEATS = "select * from " + Constants.SEAT_TABLE_NM + " order by " + Constants.COL_LABEL_SEAT_ROW + ", " + Constants.COL_LABEL_SEAT_NUMBER + ";";
	public static final String GET_ALL_SEATS_BY_STATUS = "select * from " + Constants.SEAT_TABLE_NM + " where " + Constants.COL_LABEL_SEAT_STATUS + " = ? order by " + Constants.COL_LABEL_SEAT_ROW + ", " + Constants.COL_LABEL_SEAT_NUMBER + ";";
	public static final String UPDATE_SEATS = "update " + Constants.SEAT_TABLE_NM + " set " + Constants.COL_LABEL_SEAT_STATUS + " = ?, " + Constants.COL_LABEL_CUSTOMER_EMAIL + " = ? where " + Constants.COL_LABEL_SEAT_ID + " in (%s);";
	public static final String INSERT_SEATS = "insert into " + Constants.SEAT_TABLE_NM + " (" + Constants.COL_LABEL_SEAT_NUMBER + ", " + Constants.COL_LABEL_SEAT_ROW + ") values (?, ?);";
	
	public static final String ADD_NEW_CUSTOMER = "insert into " + Constants.CUSTOMER_TABLE_NM + "(" + Constants.COL_LABEL_CUSTOMER_EMAIL + ") values (?);";
	public static final String DELETE_CUSTOMER = "delete from " + Constants.CUSTOMER_TABLE_NM + " where " + Constants.COL_LABEL_CUSTOMER_EMAIL + " = ?;";
	public static final String GET_CUSTOMER_BY_EMAIL_ADDRESS = "select * from " + Constants.CUSTOMER_TABLE_NM + " where " + Constants.COL_LABEL_CUSTOMER_EMAIL + " = ?;";
}
