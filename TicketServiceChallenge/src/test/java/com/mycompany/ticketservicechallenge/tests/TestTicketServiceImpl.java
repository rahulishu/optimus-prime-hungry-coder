package com.mycompany.ticketservicechallenge.tests;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mycompany.ticketservicechallenge.beans.Seat;
import com.mycompany.ticketservicechallenge.beans.SeatHold;
import com.mycompany.ticketservicechallenge.beans.Venue;
import com.mycompany.ticketservicechallenge.helpers.ConstantsHelper;
import com.mycompany.ticketservicechallenge.implementations.TicketServiceImpl;
/**
 * 
 * @author Rahul Soni
 * Junit Test cases written in TestTicketServiceImpl
 */
public class TestTicketServiceImpl {
	private TicketServiceImpl ticketServiceImpl;
	private int wait = 61000;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Venue venue = new Venue(10,5,Duration.ofSeconds(60));
		ticketServiceImpl = new TicketServiceImpl(venue);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void numSeatsAvailable() throws InterruptedException{
		try {
		int numSeats = ticketServiceImpl.numSeatsAvailable();
		assertThat(numSeats, is(equalTo(50)));
		
		Venue venue = new Venue(6,4,Duration.ofSeconds(60));
		ticketServiceImpl = new TicketServiceImpl(venue);
		numSeats = ticketServiceImpl.numSeatsAvailable();
		assertThat(numSeats, is(equalTo(24)));
		}catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is(equalTo(ConstantsHelper.INVALID_VENUE_DETAILS)));
		}		
	}
	
	@Test
	public void findAndHoldSeats() throws InterruptedException{
		try {
		int numSeats = ticketServiceImpl.numSeatsAvailable();
		assertThat(numSeats, is(equalTo(50)));
		
		SeatHold seatHold = ticketServiceImpl.findAndHoldSeats(3, "test1@test.com");
		assertThat(seatHold, notNullValue());
		assertThat(seatHold.getSeats().length,is(equalTo(3)));
		assertThat(seatHold.getCustomer().getCustomerEmail(),is(equalTo("test1@test.com")));
		assertThat(ticketServiceImpl.getHoldCount(),is(equalTo(3)));
		Seat[] seats = seatHold.getSeats();
		int i=0;
		for(Seat seat : seats) {
			if(i==5)
				i=0;
			assertThat(seat.getSeatRow(), anyOf(equalTo("1"), equalTo("2"), equalTo("3")));
			assertThat(seat.getSeatNo(),is(equalTo(++i)));
		}
		
		Thread.sleep(wait);
		
		numSeats = ticketServiceImpl.numSeatsAvailable();
		assertThat(numSeats, is(equalTo(50)));
		assertThat(ticketServiceImpl.getVenue().getHoldSeatMapper().get(seatHold.getSeatHoldId()),nullValue());
		assertThat(ticketServiceImpl.getHoldCount(),is(equalTo(0)));
		
		seatHold = ticketServiceImpl.findAndHoldSeats(3, "test1@test.com");
		assertThat(seatHold, notNullValue());
		assertThat(seatHold.getSeats().length,is(equalTo(3)));
		assertThat(ticketServiceImpl.getHoldCount(),is(equalTo(3)));
		seats = seatHold.getSeats();
		i=0;
		for(Seat seat : seats) {
			if(i==5)
				i=0;
			assertThat(seat.getSeatRow(), anyOf(equalTo("1"), equalTo("2"), equalTo("3")));
			assertThat(seat.getSeatNo(),is(equalTo(++i)));
		}
		assertThat(seatHold.getCustomer().getCustomerEmail(),is(equalTo("test1@test.com")));

		seatHold = ticketServiceImpl.findAndHoldSeats(3, "test2@test.com");
		assertThat(seatHold, notNullValue());
		assertThat(seatHold.getSeats().length,is(equalTo(3)));
		assertThat(ticketServiceImpl.getHoldCount(),is(equalTo(6)));
		seats = seatHold.getSeats();
		for(Seat seat : seats) {
			if(i==5)
				i=0;
			assertThat(seat.getSeatRow(), anyOf(equalTo("1"), equalTo("2"), equalTo("3")));
			assertThat(seat.getSeatNo(),is(equalTo(++i)));
		}
		assertThat(seatHold.getCustomer().getCustomerEmail(),is(equalTo("test2@test.com")));
		
		numSeats = ticketServiceImpl.numSeatsAvailable();
		assertThat(numSeats, is(equalTo(44)));
		}catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), anyOf(equalTo(ConstantsHelper.INVALID_NUM_SEATS)
					,equalTo(ConstantsHelper.INVALID_NOT_ENOUGH_SEATS)
					,equalTo(ConstantsHelper.INVALID_CUSTOMER_EMAIL)));
		}	
	}
	
	@Test
	public void reserveSeats() throws InterruptedException{
		try {
		int numSeats = ticketServiceImpl.numSeatsAvailable();
		assertThat(numSeats, is(equalTo(50)));
		
		SeatHold seatHold = ticketServiceImpl.findAndHoldSeats(4, "test3@test.com");
		assertThat(seatHold, notNullValue());
		assertThat(seatHold.getSeats().length,is(equalTo(4)));
		assertThat(ticketServiceImpl.getHoldCount(),is(equalTo(4)));
		Seat[] seats = seatHold.getSeats();
		int i=0;
		for(Seat seat : seats) {
			if(i==5)
				i=0;
			assertThat(seat.getSeatRow(), anyOf(equalTo("1"), equalTo("2"), equalTo("3")));
			assertThat(seat.getSeatNo(),is(equalTo(++i)));
		}
		assertThat(seatHold.getCustomer().getCustomerEmail(),is(equalTo("test3@test.com")));
		
		String confirmationCode = ticketServiceImpl.reserveSeats(seatHold.getSeatHoldId(), seatHold.getCustomer().getCustomerEmail());
		assertThat(confirmationCode, notNullValue());
		assertThat(ticketServiceImpl.getVenue().getConfirmedSeatMapper().get(confirmationCode),notNullValue());
		assertThat(ticketServiceImpl.getConfirmedCount(),is(equalTo(4)));
		
		numSeats = ticketServiceImpl.numSeatsAvailable();
		assertThat(numSeats, is(equalTo(46)));
		}catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), anyOf(equalTo(ConstantsHelper.INVALID_SEATHOLD)
					,equalTo(ConstantsHelper.INVALID_CUSTOMER_EMAIL)));
		}
	}
	
	
}
