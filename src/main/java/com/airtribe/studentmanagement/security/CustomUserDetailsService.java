package com.airtribe.studentmanagement.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.airtribe.studentmanagement.api.repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository repo;

	public CustomUserDetailsService(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.airtribe.studentmanagement.api.entity.User u = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(),
				List.of(new SimpleGrantedAuthority(u.getRole())));
	}
}
