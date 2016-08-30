package com.teammanager.api.user.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teammanager.api.user.document.User;

public interface UserRepository extends MongoRepository<User, BigInteger> {
	
	User findByNameAndPassword(String name , String password);
	
	User findByName(String name);
}
