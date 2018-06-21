package com.family.tech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.family.tech.constant.security.RoleType;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private LimitLoginAuthenticationProvider limitLoginAuthenticationProvider;
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	// roles admin allow to access /admin/**
	// roles user allow to access /user/**
	// custom 403 access denied handler
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/home", "/about", "/about1", "/users/**", "/webjars/**", "/css/**", "/js/**",
						"/image/**", "/DataTable/**")
				.permitAll().antMatchers("/products**", "/stocks**", "/reset-user/**")
				.hasAnyAuthority(RoleType.ROLE_ADMIN.getValue(), RoleType.ROLE_REGULAR_USER.getValue()).anyRequest()
				.authenticated().and().formLogin().loginPage("/login")
				.successHandler(customAuthenticationSuccessHandler).permitAll().failureUrl("/login?error").and()
				.logout().permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	// setUserDetailsService
	// create two users, admin and user
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		limitLoginAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(limitLoginAuthenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
