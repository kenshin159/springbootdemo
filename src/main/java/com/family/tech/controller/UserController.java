package com.family.tech.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.family.tech.constant.security.RoleType;
import com.family.tech.model.security.User;
import com.family.tech.model.security.UserRole;
import com.family.tech.service.security.UserRoleService;
import com.family.tech.service.security.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@GetMapping
	public List<User> users() {
		System.out.println("SHOW ALL USER");
		return userService.findAll();
	}

	@GetMapping("/roles")
	public List<UserRole> userRoles() {
		System.out.println("SHOW ALL ROLES");
		return userRoleService.findAll();
	}

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping("/insert")
	public List<User> userInsert() {
		System.out.println("INSERT USER");
		List<User> listUser = new ArrayList<User>();
		listUser.add(new User("admin", passwordEncoder.encode("admin"), true, true));
		listUser.add(new User("jack", passwordEncoder.encode("jack"), true, true));
		listUser.add(new User("user", passwordEncoder.encode("user"), true, true));

		List<User> userSaves = userService.save(listUser);
		System.out.println("users: " + userSaves);
		return userService.findAll();
	}

	@GetMapping("/roles/insert")
	public List<UserRole> userRoleInsert() {
		System.out.println("INSERT ROLES");
		User admin = userService.findByUserName("admin");
		User user = userService.findByUserName("user");
		User jack = userService.findByUserName("jack");

		List<UserRole> listRole = new ArrayList<UserRole>();
		listRole.add(new UserRole(admin, RoleType.ROLE_ADMIN.getValue()));
		listRole.add(new UserRole(user, RoleType.ROLE_REGULAR_USER.getValue()));
		listRole.add(new UserRole(jack, RoleType.ROLE_ADMIN.getValue()));
		listRole.add(new UserRole(jack, RoleType.ROLE_REGULAR_USER.getValue()));

		List<UserRole> roleSaves = userRoleService.save(listRole);

		System.out.println("roleSaves: " + roleSaves);
		return userRoleService.findAll();
	}

}
