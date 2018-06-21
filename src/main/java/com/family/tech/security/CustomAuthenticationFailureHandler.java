package com.family.tech.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// do some logic here if you want something to be done whenever
		// the user failed logs in.

		// TODO Auto-generated method stub
		// request.setAttribute("param", "error");
		// request.setAttribute("abc", "100");
		// response.sendRedirect("/login");
	}

}
