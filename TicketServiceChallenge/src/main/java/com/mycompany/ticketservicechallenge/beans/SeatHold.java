package com.mycompany.ticketservicechallenge.beans;

import java.time.Instant;
/**
 * 
 * @author Rahul Soni
 *SeatHold Class holds the seat Hold Id(incremental counter), Customer , Seats and Seat hold expiration time information 
 */
public class SeatHold {
	private int seatHoldId;
	private Customer customer;
	private Seat[] seats;
	private Instant expiryTime;	
	/**
	 * @param seatHoldId
	 * @param customer
	 * @param seats
	 * @param expiryTime
	 */
	public SeatHold(int seatHoldId, Customer customer, Seat[] seats, Instant expiryTime) {
		super();
		this.seatHoldId = seatHoldId;
		this.customer = customer;
		this.seats = seats;
		this.expiryTime = expiryTime;
	}
	/**
	 * @return the seatHoldId
	 */
	public int getSeatHoldId() {
		return seatHoldId;
	}
	/**
	 * @param seatHoldId the seatHoldId to set
	 */
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
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
	/**
	 * @return the expiryTime
	 */
	public Instant getExpiryTime() {
		return expiryTime;
	}
	/**
	 * @param expiryTime the expiryTime to set
	 */
	public void setExpiryTime(Instant expiryTime) {
		this.expiryTime = expiryTime;
	}
	@Override
	public String toString() {
		return "SeatHold [seatHoldId=" + seatHoldId + ", customer=" + customer + ", seats=" + seats + ", expiryTime="
				+ expiryTime + "]";
	}
	
}
