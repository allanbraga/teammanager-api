package com.teammanager.api.employee.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teammanager.api.employee.document.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, BigInteger> {	
	 
}
