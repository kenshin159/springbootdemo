package com.family.tech.service.security;

import com.family.tech.model.security.UserAttempt;
import com.family.tech.service.CommonService;

public interface UserAttemptService extends CommonService<UserAttempt, Long> {

	UserAttempt getUserAttempts(String username);

}
