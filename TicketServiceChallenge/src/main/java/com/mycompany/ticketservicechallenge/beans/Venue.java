package com.mycompany.ticketservicechallenge.beans;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.mycompany.ticketservicechallenge.helpers.ConstantsHelper;
import com.mycompany.ticketservicechallenge.helpers.StatusHelper;
/**
 * 
 * @author Rahul Soni
 *Venue Class considered as initial entry point for Ticket service implementation which requires Rows, seats per row and hold duration(in seconds, default is 60 seconds) information
 *Collections allSeatMapper, holdSeatMapper and confirmedSeatMapper is used for holding All seats , Hold seats and Confirmed seats respectively.
 *Stored the last booked(either hold or confirmed) seat for eg. "1:3" i.e. (Seat Row:Seat No) as variable to start reserving seats after that
 * 
 */
public class Venue {
	private int rows;
	private int seatsPerRow;
	private int totalSeats;
	private int availableSeats;
	private Duration holdDuration;
	private HashMap<String, Seat[]> allSeatMapper;
	private HashMap<String, SeatConfirmed> confirmedSeatMapper;
	private HashMap<Integer, SeatHold> holdSeatMapper;
	private String lastBookedSeat;
	private static AtomicInteger counter = new AtomicInteger(ConstantsHelper.START_INDEX);

	public Venue(int rows, int seatsPerRow,Duration holdDuration) {
		if(rows <= 0 || seatsPerRow <= 0) {
			throw new IllegalArgumentException(ConstantsHelper.INVALID_VENUE_DETAILS);
		}else if(holdDuration != null) {
			if(holdDuration.getSeconds() <= 0) {
			throw new IllegalArgumentException(ConstantsHelper.INVALID_VENUE_DETAILS);
			}
		}
		
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		this.totalSeats = this.rows * this.seatsPerRow;
		this.availableSeats = totalSeats;
		this.holdDuration = (holdDuration == null ? Duration.ofSeconds(ConstantsHelper.DEFAULT_HOLD_DURATION) : holdDuration);
		this.allSeatMapper =  new HashMap<String, Seat[]>();
		this.confirmedSeatMapper =  new HashMap<String, SeatConfirmed>();
		this.holdSeatMapper =  new HashMap<Integer, SeatHold>();		
	}
	/**
	 * initializeSeatMapper used for initializing the allSeatMapper
	 */
	public void initializeSeatMapper(){
		for(int i=1; i<=rows; i++){
			Seat[] perRowSeats = new Seat[seatsPerRow];
			for(int j=0; j<seatsPerRow; j++){ 
				perRowSeats[j] = new Seat(j+1,Integer.toString(i),StatusHelper.AVAILABLE);
			}
			allSeatMapper.put(Integer.toString(i), perRowSeats);
		}
	}	
	
	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 * @return the seatsPerRow
	 */
	public int getSeatsPerRow() {
		return seatsPerRow;
	}
	/**
	 * @param seatsPerRow the seatsPerRow to set
	 */
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}
	/**
	 * @return the totalSeats
	 */
	public int getTotalSeats() {
		return totalSeats;
	}
	/**
	 * @param totalSeats the totalSeats to set
	 */
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	/**
	 * @return the availableSeats
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}
	/**
	 * @param availableSeats the availableSeats to set
	 */
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	/**
	 * @return the holdDuration
	 */
	public Duration getHoldDuration() {
		return holdDuration;
	}
	/**
	 * @param holdDuration the holdDuration to set
	 */
	public void setHoldDuration(Duration holdDuration) {
		this.holdDuration = holdDuration;
	}
	/**
	 * @return the allSeatMapper
	 */
	public HashMap<String, Seat[]> getAllSeatMapper() {
		return allSeatMapper;
	}
	/**
	 * @param allSeatMapper the allSeatMapper to set
	 */
	public void setAllSeatMapper(HashMap<String, Seat[]> allSeatMapper) {
		this.allSeatMapper = allSeatMapper;
	}
	/**
	 * @return the confirmedSeatMapper
	 */
	public HashMap<String, SeatConfirmed> getConfirmedSeatMapper() {
		return confirmedSeatMapper;
	}
	/**
	 * @param confirmedSeatMapper the confirmedSeatMapper to set
	 */
	public void setConfirmedSeatMapper(HashMap<String, SeatConfirmed> confirmedSeatMapper) {
		this.confirmedSeatMapper = confirmedSeatMapper;
	}
	/**
	 * @return the holdSeatMapper
	 */
	public HashMap<Integer, SeatHold> getHoldSeatMapper() {
		return holdSeatMapper;
	}
	/**
	 * @param holdSeatMapper the holdSeatMapper to set
	 */
	public void setHoldSeatMapper(HashMap<Integer, SeatHold> holdSeatMapper) {
		this.holdSeatMapper = holdSeatMapper;
	}
	/**
	 * @return the lastBookedSeat
	 */
	public String getLastBookedSeat() {
		return lastBookedSeat;
	}
	/**
	 * @param lastBookedSeat the lastBookedSeat to set
	 */
	public void setLastBookedSeat(String lastBookedSeat) {
		this.lastBookedSeat = lastBookedSeat;
	}
	@Override
	public String toString() {
		return "Venue [rows=" + rows + ", seatsPerRow=" + seatsPerRow + ", totalSeats=" + totalSeats + ", availableSeats="
				+ availableSeats + ", holdDuration=" + holdDuration + ", allSeatMapper=" + allSeatMapper
				+ ", confirmedSeatMapper=" + confirmedSeatMapper + ", holdSeatMapper=" + holdSeatMapper
				+ ", lastBookedSeat=" + lastBookedSeat + "]";
	}
	/**
	 * @return the counter
	 */
    public synchronized int getCounter() {
        return counter.getAndIncrement();
    }
	
}
