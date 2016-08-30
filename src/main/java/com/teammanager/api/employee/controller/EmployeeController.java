package com.teammanager.api.employee.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.teammanager.api.employee.document.Employee;
import com.teammanager.api.employee.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	EmployeeService employeeService; 
	
	
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET , produces = {MediaType.APPLICATION_JSON_VALUE})
	public Collection<Employee> listEmployees() {		
		return this.employeeService.listAll();
	}
	
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{employeeId}",method = RequestMethod.GET , produces = {MediaType.APPLICATION_JSON_VALUE})
	public Employee getEmployeeById(@PathVariable("employeeId")  BigInteger employeeId ) {		
		return this.employeeService.getById(employeeId);
	}
	
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST , produces = {MediaType.APPLICATION_JSON_VALUE})
	public Employee addEmployee(@Valid @RequestBody Employee employee) {
			return this.employeeService.saveOrUpdate(employee);	
	}
	
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{employeeId}" ,method = RequestMethod.DELETE , produces = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteEmployee(@PathVariable("employeeId")  BigInteger employeeId) {
			this.employeeService.delete(employeeId);	
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	void handleBadRequests(HttpServletResponse response) throws IOException {
		logger.info("Erro on EmployeeController IllegalArgumentException");
	    response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	void handleConflictRequests(HttpServletResponse response) throws IOException {
		logger.info("Erro on EmployeeController DuplicateKeyException");
	    response.sendError(HttpStatus.CONFLICT.value());
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {		
	    List<ObjectError> errors = e.getBindingResult().getAllErrors();
	    logger.info("Erro on EmployeeController MethodArgumentNotValidException");
	    return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
	}
	

}
