package com.airtribe.studentmanagement.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.airtribe.studentmanagement.exception.InvalidDataException;

public class InputValidator {
	public static void validateEmail(String email) {
		if (email == null || !email.contains("@") || email.length() < 5) {
			throw new InvalidDataException("Invalid email: " + email);
		}
	}

	public static int parseInt(String s, String field) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new InvalidDataException("Invalid integer for " + field);
		}
	}

	public static LocalDate parseDate(String s) {
		try {
			return LocalDate.parse(s);
		} catch (DateTimeParseException e) {
			throw new InvalidDataException("Invalid date format: " + s + " expected yyyy-MM-dd");
		}
	}
}
