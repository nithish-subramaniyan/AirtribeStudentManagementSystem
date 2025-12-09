package com.airtribe.studentmanagement.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Student extends Person {
	private String rollNumber;
	private LocalDate dob;
	private int year;
	private String program;
	private Map<String, Double> grades = new HashMap<>();

	public Student() {
	}

	public Student(String firstName, String lastName, String email, String rollNumber, LocalDate dob, int year,
			String program) {
		super(UUID.randomUUID().toString(), firstName, lastName, email);
		this.rollNumber = rollNumber;
		this.dob = dob;
		this.year = year;
		this.program = program;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public Map<String, Double> getGrades() {
		return grades;
	}

	public void setGrades(Map<String, Double> grades) {
		this.grades = grades;
	}

	@Override
	public String toString() {
		return String.format("Student[id=%s, roll=%s, name=%s %s, email=%s, year=%d, program=%s]", id, rollNumber,
				firstName, lastName, email, year, program);
	}
}
