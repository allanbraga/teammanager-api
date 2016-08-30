package com.teammanager.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.teammanager.api.Application;
import com.teammanager.api.employee.document.Employee;
import com.teammanager.api.employee.repository.EmployeeRepository;

@RunWith(SpringJUnit4ClassRunner.class) // 1
@SpringApplicationConfiguration(classes = Application.class) // 2
@WebAppConfiguration // 3
@IntegrationTest
public class EmployeeTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	EmployeeRepository employeeRepository;
	
	private MockMvc mvc;
	private Employee employee;

	private static final String EMPLOYEE_JSON = "{ \"employeeId\": \"al291688\", \"firstname\": \"Employee\",\"lastname\": \"Test\",\"email\": \"test@test.com\"}";
	
	private static final String INVALID_EMPLOYEE_JSON = "{\"employeeId\": \"\",\"firstname\": \"\",\"lastname\": \"\",\"email\": \"\"}";

	private static final String EMPLOYEE_JSON_DUPLICATED = "{\"employeeId\": \"al291699\",\"firstname\": \"Employee\",\"lastname\": \"Test\",\"email\": \"test_list@test.com\"}";

	public static RequestPostProcessor adminUser() {
		return SecurityMockMvcRequestPostProcessors.user("admin").password("systest01").roles("ADMIN","USER");
	}

	public static RequestPostProcessor normalUser() {
		return SecurityMockMvcRequestPostProcessors.user("user").password("systest01").roles("USER");
	}

	@Before
	public void setUp() {
		
		employee = new Employee("al291699","Employee", "Test", "test_list@test.com");
		employee = employeeRepository.save(employee);

		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Test
	public void shouldSaveWhenUserHasRole() throws Exception {
		this.mvc.perform(
				post("/employee/").contentType(MediaType.APPLICATION_JSON).content(EMPLOYEE_JSON).with(adminUser()))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.employeeId", Matchers.is("al291688")))
				.andExpect(jsonPath("$.firstname", Matchers.is("Employee")))
				.andExpect(jsonPath("$.email", Matchers.is("test@test.com")))
				.andExpect(jsonPath("$.lastname", Matchers.is("Test")));
	}
	
	@Test
	public void shouldNotSaveWhenUserDoesNotHasRole() throws Exception {
		this.mvc.perform(
				post("/employee/").contentType(MediaType.APPLICATION_JSON).content(EMPLOYEE_JSON).with(normalUser()))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void shouldNotSaveWhenUserEmailIsDuplicated() throws Exception {
		this.mvc.perform(post("/employee/").contentType(MediaType.APPLICATION_JSON).content(EMPLOYEE_JSON_DUPLICATED)
				.with(adminUser())).andExpect(status().isConflict());
	}
	
	@Test
	public void shouldNotSaveWhenUserIsInvalid() throws Exception {
		this.mvc.perform(post("/employee/").contentType(MediaType.APPLICATION_JSON).content(INVALID_EMPLOYEE_JSON)
				.with(adminUser())).andExpect(status().isBadRequest());
	}
	

	@Test
	public void shouldFetchById() throws Exception {
		this.mvc.perform(get("/employee/" + employee.getId()).with(normalUser())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.employeeId", Matchers.is(employee.getEmployeeId())))
				.andExpect(jsonPath("$.firstname", Matchers.is(employee.getFirstname())))
				.andExpect(jsonPath("$.email", Matchers.is(employee.getEmail())))
				.andExpect(jsonPath("$.lastname", Matchers.is(employee.getLastname())));
	}
	
	@Test
	public void shouldNotFetchByIdWhenUserIsNotAuthorized() throws Exception {
		this.mvc.perform(get("/employee/27153272215665986102531851877")).andExpect(status().isUnauthorized());
	}
	
	@Test
	public void shouldFetchAll() throws Exception {
		this.mvc.perform(get("/employee").with(normalUser())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.hasSize(1)));
	}	

	@Test
	public void shouldDeleteAnEmployee() throws Exception {		
		this.mvc.perform(delete("/employee/" + employee.getId()).with(adminUser())).andExpect(status().isNoContent());
	}
	
	@Test
	public void shouldNotDeleteAnEmployeeWhenUserDoesNotHasRole() throws Exception {
		
		
		this.mvc.perform(delete("/employee/" + employee.getId()).with(normalUser())).andExpect(status().isForbidden());
	}
	

	@After
	public void tearDown() {
		employeeRepository.deleteAll();
	}
}
