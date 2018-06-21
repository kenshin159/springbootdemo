package com.nash.tech.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.family.tech.controller.UserController;
import com.family.tech.model.security.User;
import com.family.tech.model.security.UserRole;
import com.family.tech.service.security.UserRoleService;
import com.family.tech.service.security.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(secure = false)
public class UserControllerRestTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	UserService userService;

	@MockBean
	UserRoleService userRoleService;

	@Test
	public void testInsertUser_status200() throws Exception {
		Mockito.when(userService.findAll()).thenReturn(new ArrayList<User>());
		Mockito.when(userService.save(new ArrayList<User>())).thenReturn(new ArrayList<User>());

		mvc.perform(get("/users/insert").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}

	@Test
	public void testUsers_status200() throws Exception {

		mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}

	@Test
	public void testUsersRoles_status200() throws Exception {

		mvc.perform(get("/users/roles").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}

	@Test
	public void testUserRole_status200() throws Exception {
		Mockito.when(userService.findByUserName(Mockito.any(String.class))).thenReturn(new User());
		Mockito.when(userRoleService.save(new ArrayList<UserRole>())).thenReturn(new ArrayList<UserRole>());
		Mockito.when(userRoleService.findAll()).thenReturn(new ArrayList<UserRole>());

		mvc.perform(get("/users/roles/insert").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}
}
