package com.family.tech.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.family.tech.model.security.User;
import com.family.tech.repository.security.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findById(String id) {
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User object) {
		return userRepository.save(object);
	}

	@Override
	public List<User> save(List<User> objects) {
		return userRepository.save(objects);
	}

	@Override
	public void delete(User objects) {
		userRepository.delete(objects);

	}

	@Override
	public void delete(List<User> objects) {
		userRepository.delete(objects);

	}

	@Override
	public User findByUserName(String username) {
		return userRepository.findTopByUsername(username);
	}

}
