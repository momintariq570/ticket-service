package com.example.ticketservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketservice.models.Seat;
import com.example.ticketservice.services.TicketService;

@RestController
public class TicketServiceController {

	@Autowired
	TicketService ticketService;
	
	@RequestMapping("/seats")
	public List<Seat> getAllSeats() {
		return ticketService.getAllSeats();
	}
	
	@RequestMapping("/seats-by-status")
	public List<Seat> getAllSeatsByStatus(@RequestParam(value="status", defaultValue="available") String status) {
		return ticketService.getSeatsByStatus(status);
	} 
}
