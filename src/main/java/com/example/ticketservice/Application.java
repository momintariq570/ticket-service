package com.example.ticketservice;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	TicketService ticketService;

	@Value("${spring.ticket.service.max.hold.seconds}")
	private int maxHoldSeconds;
	
	@Value("${spring.ticket.service.max.threads}")
	private int maxThreads;
	
	private ExecutorService executorService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... arg0) throws Exception {

		executorService = Executors.newFixedThreadPool(maxThreads);
		printAllSeats();
		List<Seat> seatsAvailable = ticketService.getSeatsByStatus(Constants.SEAT_AVAILABLE);
		Scanner scanner = null;

		// Accept new reservations until there are no seats left
		while (seatsAvailable.size() > 0) {
			scanner = new Scanner(System.in);

			// Ask for the customer's email
			System.out.println("Customer email: ");
			String customerEmail = scanner.nextLine();
			boolean newCustomer = ticketService.insertNewCustomer(customerEmail);
			if (!newCustomer) {
				continue;
			}

			// Ask customer how many seats to reserve
			System.out.println("No. of seat(s) wanted: ");
			int numSeats = Integer.parseInt(scanner.nextLine());
			SeatHold seatHold = null; 
			while(seatHold == null) {
				seatHold = ticketService.findAndHoldSeats(numSeats, customerEmail);
				if(seatHold == null) {
					System.out.println("No. of seat(s) wanted: ");
					numSeats = Integer.parseInt(scanner.nextLine());
				}
			}
			System.out.println(numSeats + " seat(s) held for " + customerEmail);

			printAllSeats();

			// Create background task to cancel the held seat(s) after certain amount of time
			Future<?> futureTask = executorService.submit(createExecutableFutureTask(seatHold));

			// Ask customer to reserve the held seat(s)
			System.out.println("Reserve seat(s) for " + customerEmail + " (yes or no)? ");
			boolean reserveBoolean = scanner.nextLine().equalsIgnoreCase("yes") ? true : false;
			// If the seat(s) are still on hold i.e., the background task is still running
			// and customer wants to reserve the seat(s), then reserve them
			if (reserveBoolean) {
				if(!futureTask.isDone()) {
					String confirmationCode = ticketService.reserveSeats(0, customerEmail);
					System.out.println(numSeats + " seat(s) reserved for " + customerEmail + " (confirmation code: "
							+ confirmationCode + ")");	
				}
			// If the seat(s) are still on hold i.e., the background task is still running
			// and customer wants to cancel the held seat(s), then release the seat(s) to the public
			} else {
				if(!futureTask.isDone()) {
					ticketService.releaseSeats(customerEmail);
					ticketService.deleteCustomer(customerEmail);
					System.out.println(numSeats + " seat(s) released for " + customerEmail);
				}
			}
			
			// Cancel the background task of releasing the seat(s) to public
			// because they are already reserved
			futureTask.cancel(true);
			printAllSeats();
			seatsAvailable = ticketService.getSeatsByStatus(Constants.SEAT_AVAILABLE);
		}

		scanner.close();
		System.out.println("All seats are reserved");
	}

	/*
	 * Creates a FutureTask to cancel the hold on seat(s) after some specified time.
	 * This task could be cancelled by the parent thread if customer reserves the seat(s). 
	 * @param seatHold SeatHold object that contains information about a customer
	 * and the seat(s) held
	 * @return FutureTask<SeatHold> FutureTask of monitoring the time seat(s) are held
	 * for a customer
	 */
	private FutureTask<SeatHold> createExecutableFutureTask(SeatHold seatHold) {
		FutureTask<SeatHold> futureTask = new FutureTask<SeatHold>(new Runnable() {
			@Override
			public void run() {
				// Allow the customer some time to reserve the held seat(s)
				try {
					Thread.sleep(maxHoldSeconds * 1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				
				// Release the held seat(s) to the public if they are not reserved
				String customerEmail = seatHold.getCustomerEmail();
				int numSeatsHeld = ticketService
						.getAllSeatsByEmailByStatus(customerEmail, Constants.SEAT_HELD).size();
				ticketService.releaseSeats(seatHold.getCustomerEmail());
				ticketService.deleteCustomer(customerEmail);
				System.out.println(numSeatsHeld + " seats released for " + customerEmail);
				printAllSeats();
			}
		}, null);
		return futureTask;
	}

	/*
	 * Prints detailed information about all seats
	 */
	private void printAllSeats() {
		System.out.println(
				"-------------------------------------------------------------------------------------------------");
		ticketService.getAllSeats().forEach(seat -> {
			System.out.println(seat.toString());
		});
		System.out.println(
				"-------------------------------------------------------------------------------------------------");
	}
}
