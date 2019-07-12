package com.mycompany.ticketservicechallenge.beans;
/**
 * 
 * @author Rahul Soni
 *Customer Class holds the customerEmail information 
 */
public class Customer {
	private String customerEmail;
	
	/**
	 * @param customerEmail
	 */
	public Customer(String customerEmail) {
		super();
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public String toString() {
		return "Customer [ customerEmail=" + customerEmail + "]";
	}
	
	
	
}
