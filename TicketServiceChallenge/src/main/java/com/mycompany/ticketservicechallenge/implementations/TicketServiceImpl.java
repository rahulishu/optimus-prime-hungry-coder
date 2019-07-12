package com.mycompany.ticketservicechallenge.implementations;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.mycompany.ticketservicechallenge.beans.Customer;
import com.mycompany.ticketservicechallenge.beans.Seat;
import com.mycompany.ticketservicechallenge.beans.SeatConfirmed;
import com.mycompany.ticketservicechallenge.beans.SeatHold;
import com.mycompany.ticketservicechallenge.beans.Venue;
import com.mycompany.ticketservicechallenge.helpers.ConstantsHelper;
import com.mycompany.ticketservicechallenge.helpers.StatusHelper;
import com.mycompany.ticketservicechallenge.helpers.ValidationHelper;
import com.mycompany.ticketservicechallenge.interfaces.TicketService;
/**
 * 
 * @author Rahul Soni
 *TicketServiceImpl Class holds the information of Venue, holdCount and confirmedCount 
 * 
 */
public class TicketServiceImpl implements TicketService {
	private Venue venue;
	private int holdCount;
	private int confirmedCount;
	
	/**
	 * @param venue
	 */
	public TicketServiceImpl(Venue venue) {
		super();
		this.venue = venue;
		this.venue.initializeSeatMapper();
	}
	
	/**
	 * 
	 * To find the number of seats available within the venue
	 */
	@Override
	public int numSeatsAvailable() {
		releaseExpiry(Instant.now(),true,0);
		return getActualAvailablity();
	}
	
	/**
	 * 
	 * @param now
	 * @param checkAll
	 * @param seatHoldId
	 * releaseExpiry method considered for releasing any expired hold seats and make it available for new booking
	 */
    private synchronized void releaseExpiry(Instant now,boolean checkAll,int seatHoldId) {
    	HashMap<Integer, SeatHold> holdSeatMapper = venue.getHoldSeatMapper();
    	HashMap<String, Seat[]> allSeatMapper = venue.getAllSeatMapper();
    	if(checkAll) {
        	for(Map.Entry<Integer, SeatHold> entry : holdSeatMapper.entrySet()) {
        		if(!entry.getValue().getExpiryTime().isAfter(now)) {
        			Seat[] seats = entry.getValue().getSeats();
        			for(Seat seat : seats) {
            			allSeatMapper.get(seat.getSeatRow())
    		    			[seat.getSeatNo()-1]
        					.setStatus(StatusHelper.AVAILABLE);
            			holdCount--;
        			}
        			reAssignLastSeatBooked(seats);
        			holdSeatMapper.remove(entry.getKey());
        		}
        	}
    	}else {
    		SeatHold seatHold = holdSeatMapper.get(seatHoldId);
    		if(!seatHold.getExpiryTime().isAfter(now)) {
    			Seat[] seats = seatHold.getSeats();
    			for(Seat seat : seats) {
        			allSeatMapper.get(seat.getSeatRow())
		    			[seat.getSeatNo()-1]
    					.setStatus(StatusHelper.AVAILABLE);
        			holdCount--;
    			}
    			reAssignLastSeatBooked(seats);
    			holdSeatMapper.remove(seatHoldId);
    		}
    	}
    	venue.setHoldSeatMapper(holdSeatMapper);
    	venue.setAllSeatMapper(allSeatMapper);
    }
    
    /**
     * 
     * @return int actual availability 
     */
    private int getActualAvailablity() {
    	int countAvailable = venue.getTotalSeats() - holdCount - confirmedCount;
    	venue.setAvailableSeats(countAvailable);
    	return countAvailable;
    }
    /**
     * 
     * @param seats
     * After releasing the seats , method reAssignLastSeatBooked will be executed for updating the lastBookedSeat variable to holds first available seat.
     */
    public void reAssignLastSeatBooked(Seat[] seats) {
		String[] lastBookedSeat = venue.getLastBookedSeat() == null 
				? ConstantsHelper.DEFAULT_LAST_SEAT.split(":") : venue.getLastBookedSeat().split(":");
		int seatRow = Integer.parseInt(lastBookedSeat[0]);
		int seatNo = Integer.parseInt(lastBookedSeat[1]);
		int holdfirstSeatRow = Integer.parseInt(seats[0].getSeatRow());
		int holdfirstSeatNo = seats[0].getSeatNo();
		if(holdfirstSeatRow <= seatRow && holdfirstSeatNo <= seatNo) {
			if(holdfirstSeatNo-1 == 0 && holdfirstSeatRow != 1) 
			venue.setLastBookedSeat((holdfirstSeatRow-1)+ ":" +(holdfirstSeatNo-1));
			else
			venue.setLastBookedSeat(holdfirstSeatRow+ ":" +(holdfirstSeatNo-1));	
		}
    }
    
	/**
	 * 
	 * To find and hold the best available seats on behalf of a customer
	 */
    @Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		
		if(numSeats <= 0) {
			throw new IllegalArgumentException(ConstantsHelper.INVALID_NUM_SEATS);
		}else if(numSeatsAvailable() < numSeats) {
			throw new IllegalArgumentException(ConstantsHelper.INVALID_NOT_ENOUGH_SEATS);
		}else if(!ValidationHelper.isValidEmail(customerEmail)) {
			throw new IllegalArgumentException(ConstantsHelper.INVALID_CUSTOMER_EMAIL);
		}
		
		Seat[] seats = findBestAvailableSeats(numSeats);
		HashMap<Integer, SeatHold> holdSeatMapper = venue.getHoldSeatMapper();
		int seatHoldId = venue.getCounter();
		SeatHold seatHold = new SeatHold(seatHoldId
				,new Customer(customerEmail)
				,seats
				,Instant.now().plusSeconds(venue.getHoldDuration().getSeconds()));
		holdSeatMapper.put(seatHoldId,seatHold);
		holdCount += seats.length;
		venue.setHoldSeatMapper(holdSeatMapper);

		return seatHold;
	}
	
    /**
     * 
     * @param numSeats
     * @return
     * Created algorithm/code in findBestAvailableSeats method for finding best seat based on Last booked seat location.
     */
	private Seat[] findBestAvailableSeats(int numSeats) {
		Seat[] seats = new Seat[numSeats];
		HashMap<String, Seat[]> allSeatMapper = venue.getAllSeatMapper();
		String[] lastBookedSeat = venue.getLastBookedSeat() == null 
				? ConstantsHelper.DEFAULT_LAST_SEAT.split(":") : venue.getLastBookedSeat().split(":");
		int seatRow = Integer.parseInt(lastBookedSeat[0]);
		int seatNo = Integer.parseInt(lastBookedSeat[1]);
		for(int i=0;i<numSeats;) {
			if(seatNo == venue.getSeatsPerRow()) {
				seatRow++;
				seatNo = 0;
			}
			Seat[] iSeats = allSeatMapper.get(Integer.toString(seatRow));
			if(iSeats[seatNo].getStatus() == StatusHelper.AVAILABLE) {
				iSeats[seatNo].setStatus(StatusHelper.HOLD);
				seats[i] = iSeats[seatNo];
				allSeatMapper.put(Integer.toString(seatRow), iSeats);
				i++;
			}
			seatNo++;
		}
		venue.setLastBookedSeat(seatRow +":"+seatNo);
		venue.setAllSeatMapper(allSeatMapper);

		return seats;
	}
	
	/**
	 * Reserve and commit a specific group of held seats for a customer
	 */
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		releaseExpiry(Instant.now(),false,seatHoldId);
    	HashMap<Integer, SeatHold> holdSeatMapper = venue.getHoldSeatMapper();
    	HashMap<String, Seat[]> allSeatMapper = venue.getAllSeatMapper();
    	HashMap<String, SeatConfirmed> confirmedSeatMapper = venue.getConfirmedSeatMapper();
		if(!holdSeatMapper.containsKey(seatHoldId)) {
			throw new IllegalArgumentException(ConstantsHelper.INVALID_SEATHOLD);
		}else if(!ValidationHelper.isValidEmail(customerEmail)) {
			throw new IllegalArgumentException(ConstantsHelper.INVALID_CUSTOMER_EMAIL);
		}
		Seat[] seats = holdSeatMapper.get(seatHoldId).getSeats();
		for(Seat seat : seats) {
			seat.setStatus(StatusHelper.CONFIRMED);
			allSeatMapper.get(seat.getSeatRow())[seat.getSeatNo()].setStatus(StatusHelper.CONFIRMED);
		}
		String confirmationCode = ConstantsHelper.SEAT_PREFIX + seatHoldId;
		SeatConfirmed seatConfirmed = new SeatConfirmed(confirmationCode
				,holdSeatMapper.get(seatHoldId).getCustomer()
				,seats);
		confirmedCount += seats.length;
		confirmedSeatMapper.put(confirmationCode, seatConfirmed);
		holdCount -= seats.length;
		holdSeatMapper.remove(seatHoldId);
		
		venue.setAllSeatMapper(allSeatMapper);
		venue.setHoldSeatMapper(holdSeatMapper);
		venue.setConfirmedSeatMapper(confirmedSeatMapper);
    	
		return confirmationCode;
	}
	
	/**
	 * @return the venue
	 */
	public Venue getVenue() {
		return venue;
	}

	/**
	 * @param venue the venue to set
	 */
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	/**
	 * @return the holdCount
	 */
	public int getHoldCount() {
		return holdCount;
	}

	/**
	 * @param holdCount the holdCount to set
	 */
	public void setHoldCount(int holdCount) {
		this.holdCount = holdCount;
	}

	/**
	 * @return the confirmedCount
	 */
	public int getConfirmedCount() {
		return confirmedCount;
	}

	/**
	 * @param confirmedCount the confirmedCount to set
	 */
	public void setConfirmedCount(int confirmedCount) {
		this.confirmedCount = confirmedCount;
	}	
}
