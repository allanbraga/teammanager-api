package com.teammanager.api.user.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.teammanager.api.user.document.User;
import com.teammanager.api.user.repository.UserRepository;

@Service 
public class UserServiceImpl implements UserService {
	
	private static final String ROLE_PREFIX = "ROLE_";

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getById(BigInteger userId) {		
		return userRepository.findOne(userId);
	}	

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> listAll() {
		return userRepository.findAll();
	}

	@Override
	public void delete(BigInteger userId) {
		userRepository.delete(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		User user = this.userRepository.findByName(username);
		return new  org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),getUserAuthorities(user.getRoles()));
	}	
	
	private List<GrantedAuthority> getUserAuthorities(String... roles){		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();		
		for (String role : roles) {
			if (role.startsWith(ROLE_PREFIX)) {				
				authorities.add(new SimpleGrantedAuthority(role));
			}else{
				authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
			}
		}		
		return authorities;
		
	}

}
