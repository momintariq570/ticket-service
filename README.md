Ticket Service

This application implements a simple ticket service that facilitates the discovery, temporary 
hold and final reservation of seats within a high-demand performance venue.

The application utilizes Spring Boot and is packaged as a jar file.  It can be launched
by running Application.java file from the IDE or by executing mvn spring-boot:run command
from the terminal.

At the moment, the application only contains back end code with user input coming from the
console.  Front end code might be added in the future.  The application launches on an
embedded Tomcat server, so converting this application to a complete web application is possible
with minimal changes to existing code.

The application also relies on an embedded, in-memory H2 database to store seats and customers.
The H2 console can be viewed on localhost:8080/h2. Use jdbc:h2:mem:testdb as the url to access
the database.

Following assumptions were made:
1) The application will process 1 seat hold and reserve request at a time.
2) Seat(s) will be held for 10 seconds.
3) 1 background thread will be launched to cancel the seats held after 10 seconds unless the
customer reserves the seats.  If the customer reserves the seats, the background thread's task 
will be cancelled. 
