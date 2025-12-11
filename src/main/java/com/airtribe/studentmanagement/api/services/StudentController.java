package com.airtribe.studentmanagement.api.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airtribe.studentmanagement.api.entity.Student;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	private final StudentService svc;

	public StudentController(StudentService svc) {
		this.svc = svc;
	}

	@GetMapping
	public List<Student> list() {
		return svc.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Student> get(@PathVariable String id) {
		return svc.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Student> create(@RequestBody Student s) {
		Student saved = svc.create(s);
		return ResponseEntity.status(201).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> update(@PathVariable String id, @RequestBody Student s) {
		return svc.findById(id).map(existing -> {
			s.setId(existing.getId());
			return ResponseEntity.ok(svc.update(s));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		svc.delete(id);
		return ResponseEntity.noContent().build();
	}
}
