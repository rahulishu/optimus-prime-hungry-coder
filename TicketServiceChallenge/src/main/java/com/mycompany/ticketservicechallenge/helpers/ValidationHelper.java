package com.mycompany.ticketservicechallenge.helpers;

import org.apache.commons.validator.routines.EmailValidator;
/**
 * 
 * @author Rahul Soni
 * Validation Helper class for providing common validations
 */
public class ValidationHelper {
	public static boolean isValidEmail(String email) {
		if (email == null)
		{
			return false;
		}
		else if("".equals(email.trim())) {
			return false;
		}
		email = email.trim();
		EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(email);
	}
}
