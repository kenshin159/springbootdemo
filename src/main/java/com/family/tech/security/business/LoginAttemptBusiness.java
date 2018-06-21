package com.family.tech.security.business;

public interface LoginAttemptBusiness {

	void updateUserAndUserAttemptWhenLoginFail(String username);

	void resetUserAttempt(String username);

	void resetUserWhenLocked(String username);
}
