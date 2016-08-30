package com.teammanager.api.user.document;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.teammanager.api.core.AbstractDocument;

@Document
public class User extends AbstractDocument {
	
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();	

	private String name;

	private String password;

	private String[] roles;

	public void setPassword(String password) {
		this.password = PASSWORD_ENCODER.encode(password);
	}

	protected User() {}

	public User(String name, String password, String... roles) {

		this.name = name;
		this.setPassword(password);
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}
	
	
	

}
