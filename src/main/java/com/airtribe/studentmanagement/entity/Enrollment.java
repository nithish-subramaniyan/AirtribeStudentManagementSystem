package com.airtribe.studentmanagement.entity;

import java.time.LocalDate;
import java.util.UUID;

public class Enrollment {
	private String id;
	private String studentId;
	private String courseId;
	private LocalDate enrollmentDate;
	private String status; // ENROLLED / DROPPED

	public Enrollment() {
	}

	public Enrollment(String studentId, String courseId) {
		this.id = UUID.randomUUID().toString();
		this.studentId = studentId;
		this.courseId = courseId;
		this.enrollmentDate = LocalDate.now();
		this.status = "ENROLLED";
	}

	public String getId() {
		return id;
	}

	public String getStudentId() {
		return studentId;
	}

	public String getCourseId() {
		return courseId;
	}

	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("Enrollment[id=%s, student=%s, course=%s, date=%s, status=%s]", id, studentId, courseId,
				enrollmentDate, status);
	}
}
