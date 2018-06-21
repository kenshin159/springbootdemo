package com.family.tech.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.family.tech.model.security.UserAttempt;
import com.family.tech.security.business.LoginAttemptBusiness;
import com.family.tech.service.security.UserAttemptService;

//@Component("authenticationProvider")
@Component
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	LoginAttemptBusiness loginAttemptBusiness;

	@Autowired
	UserAttemptService userAttemptService;

	@Autowired
	MyUserDetailService myUserDetailService;

	@Autowired
	public void setUserDetailsService() {
		this.setUserDetailsService(myUserDetailService);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			Authentication auth = super.authenticate(authentication);
			// if reach here, means login success, else exception will be thrown
			// reset the user_attempts
			loginAttemptBusiness.resetUserAttempt(authentication.getName());
			return auth;
		} catch (BadCredentialsException e) {
			// invalid login, update to user_attempts
			loginAttemptBusiness.updateUserAndUserAttemptWhenLoginFail(authentication.getName());
			throw e;
		} catch (LockedException e) {
			// this user is locked!
			String error = "";
			UserAttempt userAttempts = userAttemptService.getUserAttempts(authentication.getName());
			if (userAttempts != null) {
				Date lastAttempts = userAttempts.getLastModified();
				error = "User account is locked! <br><br>Username : " + authentication.getName()
						+ "<br>Last Attempts : " + lastAttempts;
			} else {
				error = e.getMessage();
			}
			throw new LockedException(error);
		}

	}

}