package com.family.tech.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.family.tech.model.security.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
