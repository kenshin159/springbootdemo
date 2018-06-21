package com.nash.tech.service.security;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.family.tech.model.security.User;
import com.family.tech.repository.security.UserRepository;
import com.family.tech.service.security.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

	@MockBean
	UserRepository userRepository;

	@Autowired
	UserService userService;

	User user;
	List<User> listUser;

	@Before
	public void setup() throws Exception {
		user = new User();
		user.setUsername("userName");
		user.setPassword("password");
		user.setEnabled(true);
		Mockito.when(userRepository.findOne("userName")).thenReturn(user);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		listUser = new ArrayList<User>();
		listUser.add(user);
		Mockito.when(userRepository.save(listUser)).thenReturn(listUser);
		Mockito.when(userRepository.findAll()).thenReturn(listUser);

	}

	@Test
	public void testFindAll() {
		List<User> result = userService.findAll();
		User userResult = result.get(0);
		assertEquals(userResult.getUsername(), "userName");
		assertEquals(userResult.getPassword(), "password");
		assertEquals(userResult.isEnabled(), true);
	}

	@Test
	public void shouldFindByIdSuccess() {
		User userResult = userService.findById("userName");
		assertEquals(userResult.getUsername(), "userName");
		assertEquals(userResult.getPassword(), "password");
		assertEquals(userResult.isEnabled(), true);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldSaveSuccess() {
		User userSave = userService.save(user);
		assertEquals(userSave.getUsername(), "userName");
		assertEquals(userSave.getPassword(), "password");
		assertEquals(userSave.isEnabled(), true);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldSaveListUserSuccess() {
		List<User> result = userService.save(listUser);
		User userSave = result.get(0);
		assertEquals(userSave.getUsername(), "userName");
		assertEquals(userSave.getPassword(), "password");
		assertEquals(userSave.isEnabled(), true);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldDeleteSuccess() {
		userService.delete(user);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldDeleteListUserSuccess() {
		userService.delete(listUser);
	}
}
