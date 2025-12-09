package com.airtribe.studentmanagement.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.airtribe.studentmanagement.entity.Student;
import com.airtribe.studentmanagement.exception.InvalidDataException;
import com.airtribe.studentmanagement.util.JsonUtil;

public class StudentService {
	private static final StudentService INSTANCE = new StudentService();
	private final Map<String, Student> store = new LinkedHashMap<>();
	private final JsonUtil json = JsonUtil.getInstance();
	private final String PATH = "students.json";

	private StudentService() {
	}

	public static StudentService getInstance() {
		return INSTANCE;
	}

	public Student createStudent(Student s) {
		if (s.getEmail() == null)
			throw new InvalidDataException("Email required");
		store.put(s.getId(), s);
		save();
		return s;
	}

	public Optional<Student> findById(String id) {
		return Optional.ofNullable(store.get(id));
	}

	public List<Student> listAll() {
		return new ArrayList<>(store.values());
	}

	public void updateStudent(Student s) {
		if (!store.containsKey(s.getId()))
			throw new InvalidDataException("Student not found: " + s.getId());
		store.put(s.getId(), s);
		save();
	}

	public void deleteStudent(String id) {
		store.remove(id);
		save();
	}

	public void save() {
		json.writeList(PATH, listAll());
	}

	public void load() {
		List<Student> list = json.readList(PATH, Student.class);
		store.clear();
		list.forEach(s -> store.put(s.getId(), s));
	}
}
