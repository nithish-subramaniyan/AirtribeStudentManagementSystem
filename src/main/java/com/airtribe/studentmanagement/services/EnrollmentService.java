package com.airtribe.studentmanagement.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.airtribe.studentmanagement.entity.Course;
import com.airtribe.studentmanagement.entity.Enrollment;
import com.airtribe.studentmanagement.entity.Student;
import com.airtribe.studentmanagement.exception.InvalidDataException;
import com.airtribe.studentmanagement.util.JsonUtil;

public class EnrollmentService {
	private static final EnrollmentService INSTANCE = new EnrollmentService();
	private final Map<String, Enrollment> store = new LinkedHashMap<>();
	private final JsonUtil json = JsonUtil.getInstance();
	private final String PATH = "enrollments.json";

	private EnrollmentService() {
	}

	public static EnrollmentService getInstance() {
		return INSTANCE;
	}

	public Enrollment enroll(String studentId, String courseId) {
		StudentService ss = StudentService.getInstance();
		CourseService cs = CourseService.getInstance();

		Student s = ss.findById(studentId)
				.orElseThrow(() -> new InvalidDataException("Student not found: " + studentId));

		Course c = cs.findById(courseId).orElseThrow(() -> new InvalidDataException("Course not found: " + courseId));

		long enrolledCount = store.values().stream()
				.filter(e -> e.getCourseId().equals(courseId) && "ENROLLED".equals(e.getStatus())).count();

		if (enrolledCount >= c.getMaxSeats()) {
			throw new InvalidDataException("Course full");
		}

		Enrollment e = new Enrollment(s.getId(), c.getId());

		store.put(e.getId(), e);
		save();
		return e;
	}

	public List<Enrollment> listByStudent(String studentId) {
		return store.values().stream().filter(e -> e.getStudentId().equals(studentId)).collect(Collectors.toList());
	}

	public void dropEnrollment(String enrollmentId) {
		Enrollment e = store.get(enrollmentId);
		if (e != null) {
			e.setStatus("DROPPED");
		}
		save();
	}

	public void save() {
		json.writeList(PATH, new ArrayList<>(store.values()));
	}

	public void load() {
		List<Enrollment> list = json.readList(PATH, Enrollment.class);
		store.clear();
		list.forEach(e -> store.put(e.getId(), e));
	}
}
