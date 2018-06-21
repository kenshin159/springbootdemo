package com.family.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.family.tech.security.business.LoginAttemptBusiness;

@Controller
public class UsertAttemptController {

	@Autowired
	LoginAttemptBusiness loginAttemptBusiness;

	@GetMapping("/reset-user/{username}")
	@ResponseBody
	public String resetUser(@PathVariable("username") String username) {
		loginAttemptBusiness.resetUserWhenLocked(username);
		String message = "You have reseted account " + username + " successfully.";
		return message;
	}
}
