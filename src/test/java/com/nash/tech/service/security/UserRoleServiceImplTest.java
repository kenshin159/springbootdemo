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
import com.family.tech.model.security.UserRole;
import com.family.tech.repository.security.UserRoleRepository;
import com.family.tech.service.security.UserRoleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleServiceImplTest {

	@MockBean
	UserRoleRepository userRoleRepository;

	@Autowired
	UserRoleService userRoleService;

	UserRole userRole;
	List<UserRole> listUserRole;
	int userRoleId;

	@Before
	public void setup() throws Exception {

		User user = new User();
		user.setUsername("userName");
		user.setPassword("password");
		user.setEnabled(true);

		userRole = new UserRole();
		userRoleId = 1;
		userRole.setUserRoleId(userRoleId);
		userRole.setRole("ADMIN");
		userRole.setUser(user);

		Mockito.when(userRoleRepository.findOne(userRoleId)).thenReturn(userRole);
		Mockito.when(userRoleRepository.save(Mockito.any(UserRole.class))).thenReturn(userRole);

		listUserRole = new ArrayList<UserRole>();
		listUserRole.add(userRole);
		Mockito.when(userRoleRepository.save(listUserRole)).thenReturn(listUserRole);
		Mockito.when(userRoleRepository.findAll()).thenReturn(listUserRole);

	}

	@Test
	public void testFindAll() {
		List<UserRole> result = userRoleService.findAll();
		UserRole userResult = result.get(0);
		User user = userResult.getUser();
		assertEquals(userResult.getRole(), "ADMIN");
		assertEquals(user.getUsername(), "userName");
		assertEquals(user.getPassword(), "password");
		assertEquals(user.isEnabled(), true);
	}

	@Test
	public void shouldFindByIdSuccess() {
		UserRole result = userRoleService.findById(userRoleId);
		User user = result.getUser();
		assertEquals(result.getRole(), "ADMIN");
		assertEquals(user.getUsername(), "userName");
		assertEquals(user.getPassword(), "password");
		assertEquals(user.isEnabled(), true);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldSaveSuccess() {
		UserRole userSave = userRoleService.save(userRole);
		assertEquals(userSave.getRole(), "ADMIN");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldSaveListUserSuccess() {
		List<UserRole> result = userRoleService.save(listUserRole);
		UserRole userSave = result.get(0);
		assertEquals(userSave.getRole(), "ADMIN");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldDeleteSuccess() {
		userRoleService.delete(userRole);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldDeleteListUserSuccess() {
		userRoleService.delete(listUserRole);
	}
}
