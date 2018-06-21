package com.family.tech.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.family.tech.model.security.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findTopByUsername(String username);

}
