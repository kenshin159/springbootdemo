package com.family.tech.service.security;

import com.family.tech.model.security.User;
import com.family.tech.service.CommonService;

public interface UserService extends CommonService<User, String> {

	User findByUserName(String username);

}
