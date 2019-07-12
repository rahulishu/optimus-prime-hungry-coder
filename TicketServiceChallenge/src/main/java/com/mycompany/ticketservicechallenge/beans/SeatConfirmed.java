package com.mycompany.ticketservicechallenge.beans;
/**
 * 
 * @author Rahul Soni
 *SeatConfirmed Class holds the confirmation code (in prefix of "CNF000"), Customer and Seats information 
 */
public class SeatConfirmed {
	private String seatConfirmedId;
	private Customer customer;
	private Seat[] seats;
	
	/**
	 * @param seatConfirmedId
	 * @param customer
	 * @param seats
	 */
	public SeatConfirmed(String seatConfirmedId, Customer customer, Seat[] seats) {
		super();
		this.seatConfirmedId = seatConfirmedId;
		this.customer = customer;
		this.seats = seats;
	}
	/**
	 * @return the seatConfirmedId
	 */
	public String getSeatConfirmedId() {
		return seatConfirmedId;
	}
	/**
	 * @param seatConfirmedId the seatConfirmedId to set
	 */
	public void setSeatConfirmedId(String seatConfirmedId) {
		this.seatConfirmedId = seatConfirmedId;
	}
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	/**
	 * @return the seats
	 */
	public Seat[] getSeats() {
		return seats;
	}
	/**
	 * @param seats the seats to set
	 */
	public void setSeats(Seat[] seats) {
		this.seats = seats;
	}
	@Override
	public String toString() {
		return "SeatConfirmed [seatConfirmedId=" + seatConfirmedId + ", customer=" + customer + ", seats=" + seats
				+ "]";
	}
}
