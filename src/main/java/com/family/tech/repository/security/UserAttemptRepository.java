package com.family.tech.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.family.tech.model.security.UserAttempt;

public interface UserAttemptRepository extends JpaRepository<UserAttempt, Long> {

	UserAttempt findByUsername(String username);
}
