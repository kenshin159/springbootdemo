package com.family.tech.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.family.tech.model.security.UserAttempt;
import com.family.tech.repository.security.UserAttemptRepository;

@Component
public class UserAttemptServiceImpl implements UserAttemptService {

	@Autowired
	private UserAttemptRepository userAttemptRepository;

	@Override
	public UserAttempt findById(Long id) {
		return userAttemptRepository.findOne(id);
	}

	@Override
	public List<UserAttempt> findAll() {
		return userAttemptRepository.findAll();
	}

	@Override
	public UserAttempt save(UserAttempt object) {
		return userAttemptRepository.save(object);
	}

	@Override
	public List<UserAttempt> save(List<UserAttempt> objects) {
		return userAttemptRepository.save(objects);
	}

	@Override
	public void delete(UserAttempt object) {
		userAttemptRepository.delete(object);

	}

	@Override
	public void delete(List<UserAttempt> objects) {
		userAttemptRepository.delete(objects);
	}

	@Override
	public UserAttempt getUserAttempts(String username) {
		return userAttemptRepository.findByUsername(username);
	}

}
