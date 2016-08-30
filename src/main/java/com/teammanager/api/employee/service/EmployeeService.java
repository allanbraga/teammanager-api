package com.teammanager.api.employee.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.teammanager.api.employee.document.Employee;

public interface EmployeeService {
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	Employee saveOrUpdate(Employee employee);
	
	@PreAuthorize("hasRole('ROLE_USER')")
	List<Employee> listAll();
	
	@PreAuthorize("hasRole('ROLE_USER')")
	Employee getById(BigInteger employeeID);
	
		
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(BigInteger employeeID);

}
