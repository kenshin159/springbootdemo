package com.family.tech.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.family.tech.model.security.UserRole;
import com.family.tech.repository.security.UserRoleRepository;

@Component
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserRole findById(Integer id) {
		return userRoleRepository.findOne(id);
	}

	@Override
	public List<UserRole> findAll() {
		return userRoleRepository.findAll();
	}

	@Override
	public UserRole save(UserRole object) {
		return userRoleRepository.save(object);
	}

	@Override
	public List<UserRole> save(List<UserRole> objects) {
		return userRoleRepository.save(objects);
	}

	@Override
	public void delete(UserRole object) {
		userRoleRepository.delete(object);

	}

	@Override
	public void delete(List<UserRole> objects) {
		userRoleRepository.delete(objects);

	}

}
