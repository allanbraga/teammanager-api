package com.teammanager.api.employee.document;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.teammanager.api.core.AbstractDocument;

@Document
public class Employee extends AbstractDocument  {
	
	@NotBlank
	@Field("employeeId")
	@Indexed(unique = true)
	private String employeeId;
	
	@NotBlank
	private String firstname;
	
	@NotBlank
	private String lastname;
	

	@NotBlank
	@Field("email")
	@Indexed(unique = true)
	private String email;
		

	public Employee(String employeeId , String firstname, String lastname, String email) {
		this.employeeId = employeeId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	
	protected Employee() {

	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
