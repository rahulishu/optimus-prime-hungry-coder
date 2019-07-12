package com.mycompany.ticketservicechallenge.beans;

import com.mycompany.ticketservicechallenge.helpers.StatusHelper;
/**
 * 
 * @author Rahul Soni
 *Seat Class holds the information of Seat Number, Seat Row and Status(Available,Hold,Confirmed) of the Seat 
 */
public class Seat {
	private int seatNo;
	private String seatRow;
	private StatusHelper status;
	
	/**
	 * @param seatNo
	 * @param seatRow
	 * @param status
	 */
	public Seat(int seatNo, String seatRow, StatusHelper status) {
		super();
		this.seatNo = seatNo;
		this.seatRow = seatRow;
		this.status = status;
	}	
	/**
	 * @return the seatNo
	 */
	public int getSeatNo() {
		return seatNo;
	}
	/**
	 * @param seatNo the seatNo to set
	 */
	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}
	/**
	 * @return the seatRow
	 */
	public String getSeatRow() {
		return seatRow;
	}
	/**
	 * @param seatRow the seatRow to set
	 */
	public void setSeatRow(String seatRow) {
		this.seatRow = seatRow;
	}
	/**
	 * @return the status
	 */
	public StatusHelper getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusHelper status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Seat [seatNo=" + seatNo + ", seatRow=" + seatRow + ", status=" + status 
				+ "]";
	}

}
