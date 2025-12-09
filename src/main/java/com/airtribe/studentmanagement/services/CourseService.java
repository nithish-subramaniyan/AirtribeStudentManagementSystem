package com.airtribe.studentmanagement.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.airtribe.studentmanagement.entity.Course;
import com.airtribe.studentmanagement.util.JsonUtil;

public class CourseService {
	private static final CourseService INSTANCE = new CourseService();
	private final Map<String, Course> store = new LinkedHashMap<>();
	private final JsonUtil json = JsonUtil.getInstance();
	private final String PATH = "courses.json";

	private CourseService() {
	}

	public static CourseService getInstance() {
		return INSTANCE;
	}

	public Course createCourse(Course c) {
		store.put(c.getId(), c);
		save();
		return c;
	}

	public Optional<Course> findById(String id) {
		return Optional.ofNullable(store.get(id));
	}

	public List<Course> listAll() {
		return new ArrayList<>(store.values());
	}

	public void save() {
		json.writeList(PATH, listAll());
	}

	public void load() {
		List<Course> list = json.readList(PATH, Course.class);
		store.clear();
		list.forEach(c -> store.put(c.getId(), c));
	}
}
