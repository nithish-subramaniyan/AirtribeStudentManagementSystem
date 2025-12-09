package com.airtribe.studentmanagement.entity;

import java.util.UUID;

public class Course {
	private String id;
	private String code;
	private String title;
	private int credits;
	private int maxSeats;

	public Course() {
	}

	public Course(String code, String title, int credits, int maxSeats) {
		this.id = UUID.randomUUID().toString();
		this.code = code;
		this.title = title;
		this.credits = credits;
		this.maxSeats = maxSeats;
	}

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public int getCredits() {
		return credits;
	}

	public int getMaxSeats() {
		return maxSeats;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}

	@Override
	public String toString() {
		return String.format("Course[id=%s, %s - %s, credits=%d, seats=%d]", id, code, title, credits, maxSeats);
	}
}
