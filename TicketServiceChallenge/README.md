TicketServiceChallenge
==
Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance venue.

Testing and Building with maven
==
java and maven should be installed before testing in test system

1. Build a JAR with "mvn clean install" or "mvn clean package"
2. It will generate the jar in below project folder TicketServiceChallenge\target\TicketServiceChallenge-0.0.1-SNAPSHOT.jar
3. Testing 
	a. Test the all junit test cases with "mvn test" or "mvn -Dtest=TestTicketServiceImpl test"
	b. Running specific test cases "mvn -Dtest=TestTicketServiceImpl#numSeatsAvailable test"

Assumptions
==

1. Venue Class considered as initial entry point for Ticket service implementation which requires Rows, seats per row and hold duration(in seconds, default is 60 seconds) information
2. Collections allSeatMapper, holdSeatMapper and confirmedSeatMapper is used for holding All seats , Hold seats and Confirmed seats respectively.
3. Collection allSeatMapper is HashMap and holds the information in format HashMap<String, Seat[]>
4. Collection holdSeatMapper is HashMap and holds the information in format HashMap<Integer, SeatHold>
5. Collection confirmedSeatMapper is HashMap and holds the information in format HashMap<String, SeatConfirmed>
6. Seat Class holds the information of Seat Number, Seat Row and Status(Available,Hold,Confirmed) of the Seat
7. Customer Class holds the customerEmail information
8. SeatConfirmed Class holds the confirmation code (in prefix of "CNF000"), Customer and Seats information
9. SeatHold Class holds the seat Hold Id(incremental counter), Customer , Seats and Seat hold expiration time information
10. Stored the last booked(either hold or confirmed) seat for eg. "1:3" i.e. (Seat Row:Seat No) as variable to start reserving seats after that
11. Seat Number/seat row near to stage from left to right considered as best available seats for customer
12. Reserving adjacent seats are not considered as best. It will book sequentially what available first. So for eg. suppose customer requested 3 seats in 5x5 seats grid. only 2 seats available in 1st row left corner (1,2) and 3rd seats available in 2nd row left corner(1) then we allocate available seats for customer.
13. Created algorithm/code in findBestAvailableSeats method for finding best seat based on Last booked seat location.
14. releaseExpiry method considered for releasing any expired hold seats and make it available for new booking
15. After releasing the seats , method reAssignLastSeatBooked will be executed for updating the lastBookedSeat variable to holds first available seat.
16. If any input validation error comes it will through IllegalArgumentException with appropriate exception message
17. If seatHold got release before reserving the seats then also it will through IllegalArgumentException with appropriate message
18. Basic Junit Test methods written to test all functionalities involved , we can increase more test cases as required

Lack of License
==

This software is not licensed. Do not distribute.