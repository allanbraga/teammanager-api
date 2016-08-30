package com.teammanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.teammanager.api.user.document.User;
import com.teammanager.api.user.repository.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {
	
	private final UserRepository userRepository;

	@Autowired
	public DatabaseLoader(UserRepository userRepositoryParam) {		
		this.userRepository = userRepositoryParam;
	}
	

	//spring-boot:run
	@Override
	public void run(String... args) throws Exception {		
		User user = new User("ADMIN", "systest01", "USER","ADMIN");
		//userRepository.save(user);
	}
}
