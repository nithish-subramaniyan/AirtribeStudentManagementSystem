package com.airtribe.studentmanagement.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airtribe.studentmanagement.api.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
}
