package com.family.tech.security.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;

import com.family.tech.constant.Constant;
import com.family.tech.model.security.User;
import com.family.tech.model.security.UserAttempt;
import com.family.tech.service.security.UserAttemptService;
import com.family.tech.service.security.UserService;

@Component
public class LoginAttemptBusinessImpl implements LoginAttemptBusiness {

	@Autowired
	UserAttemptService userAttemptService;

	@Autowired
	UserService userService;

	@Override
	public void updateUserAndUserAttemptWhenLoginFail(String username) {
		if (!Constant.ADMIN_NAME.equals(username)) {
			UserAttempt userAttempt = userAttemptService.getUserAttempts(username);
			if (userAttempt == null) {// create
				User user = userService.findByUserName(username);
				if (user != null) {
					UserAttempt userAttemptNew = new UserAttempt();
					userAttemptNew.setUsername(username);
					userAttemptNew.setAttempts(1);
					userAttemptNew.setLastModified(new Date());
					userAttemptService.save(userAttemptNew);
				}
			} else {// update
				if (userAttempt.getAttempts() + 1 < Constant.MAX_ATTEMPTS) {
					userAttempt.setAttempts(userAttempt.getAttempts() + 1);
					userAttempt.setLastModified(new Date());
					userAttemptService.save(userAttempt);
				} else {
					User user = userService.findByUserName(username);
					user.setAccountNonLocked(false);
					userService.save(user);
					// throw exception
					throw new LockedException("User Account is locked!");
				}
			}
		}
	}

	@Override
	public void resetUserAttempt(String username) {
		// update user attempt
		UserAttempt userAttempt = userAttemptService.getUserAttempts(username);
		if (userAttempt != null) {
			userAttempt.setAttempts(0);
			userAttempt.setLastModified(null);
			userAttemptService.save(userAttempt);
		}
	}

	@Override
	public void resetUserWhenLocked(String username) {
		// update user
		User user = userService.findByUserName(username);
		if (user != null) {
			user.setAccountNonLocked(true);
			userService.save(user);
		}
		// update user attempt
		UserAttempt userAttempt = userAttemptService.getUserAttempts(username);
		if (userAttempt != null) {
			userAttempt.setAttempts(0);
			userAttempt.setLastModified(null);
			userAttemptService.save(userAttempt);
		}
	}

}
