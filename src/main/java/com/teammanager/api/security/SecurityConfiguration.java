package com.teammanager.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.teammanager.api.user.document.User;
import com.teammanager.api.user.service.UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userService).passwordEncoder(User.PASSWORD_ENCODER);
	}

	/**
	 * Memory way
	 * 
	 * This section defines the user accounts which can be used for
	 * authentication as well as the roles each user has.
	 */
	/*
	 * @Override public void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * auth.inMemoryAuthentication().withUser("user").password("systest01").
	 * roles("USER").and().withUser("admin")
	 * .password("systest01").roles("USER","ADMIN"); }
	 */

	/**
	 * This section defines the security policy for the app. - BASIC
	 * authentication is supported (enough for this REST-based demo) - is
	 * secured using URL security shown below - CSRF headers are disabled since
	 * we are only testing the REST interface, not a web one.
	 *
	 * NOTE: GET is not shown which defaults to permitted.
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().and().authorizeRequests().antMatchers("/index.html", "/home.html", "/login.html", "/")
				.permitAll().anyRequest().authenticated();
	}

	/**
	 * Another way to apply security instead of using annotation into the
	 * service methods This section defines the security policy for the app. -
	 * BASIC authentication is supported (enough for this REST-based demo) -
	 * /employees is secured using URL security shown below - CSRF headers are
	 * disabled since we are only testing the REST interface, not a web one.
	 *
	 */
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.httpBasic().and().authorizeRequests() .antMatchers(HttpMethod.GET,
	 * "/employee/**").hasRole("USER") .antMatchers(HttpMethod.POST,
	 * "/employee/**").hasRole("ADMIN") .antMatchers(HttpMethod.PUT,
	 * "/employee/**").hasRole("ADMIN") .antMatchers(HttpMethod.PATCH,
	 * "/employee/**").hasRole("ADMIN").and().csrf().disable(); }
	 */

}
