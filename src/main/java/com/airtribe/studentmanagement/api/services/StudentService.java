package com.airtribe.studentmanagement.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.airtribe.studentmanagement.api.entity.Student;
import com.airtribe.studentmanagement.api.repo.StudentRepository;

@Service
public class StudentService {
	private final StudentRepository repo;

	public StudentService(StudentRepository repo) {
		this.repo = repo;
	}

	public Student create(Student s) {
		return repo.save(s);
	}

	public List<Student> findAll() {
		return repo.findAll();
	}

	public Optional<Student> findById(String id) {
		return repo.findById(id);
	}

	public Student update(Student s) {
		return repo.save(s);
	}

	public void delete(String id) {
		repo.deleteById(id);
	}
}
