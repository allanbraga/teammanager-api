package com.teammanager.api.user.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.teammanager.api.user.document.User;

public interface UserService extends UserDetailsService  {
	
	User getById(BigInteger userId);
	
	User save(User user);
	
	List<User> listAll();
	
	void delete(BigInteger userId);

}
