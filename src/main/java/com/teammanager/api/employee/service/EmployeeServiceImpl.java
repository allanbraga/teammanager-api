package com.teammanager.api.employee.service;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.teammanager.api.employee.document.Employee;
import com.teammanager.api.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public Employee saveOrUpdate(Employee employee) {
		return employeeRepository.save(employee);
	}

	
	@Override
	public List<Employee> listAll() {		
		return employeeRepository.findAll();
	}

	
	@Override
	public Employee getById(BigInteger employeeID) {		
		return employeeRepository.findOne(employeeID);
	}

	
	@Override
	public void delete(BigInteger employeeID) {		
		employeeRepository.delete(employeeID);
	}
	
	protected void showUser(){		
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(principal);		
		
	}

}
