package com.airtribe.studentmanagement.api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airtribe.studentmanagement.api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
